package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.common.EventService;
import com.klab.gui.events.OpenScriptEvent;
import com.klab.gui.factories.ScriptTabFactory;
import com.klab.gui.model.ScriptContext;
import com.klab.gui.service.ScriptViewService;
import com.klab.interpreter.core.Interpreter;
import com.klab.interpreter.core.code.ScriptService;
import com.klab.interpreter.core.events.*;
import com.klab.interpreter.debug.Breakpoint;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import com.klab.interpreter.debug.BreakpointReleaseEvent;
import com.klab.interpreter.debug.BreakpointService;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.functions.external.ExternalFunctionService;
import com.klab.interpreter.translate.model.Instruction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.apache.commons.io.FilenameUtils.removeExtension;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptEditorController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptEditorController.class);
    // @FXML
    public TabPane scriptPane;
    public Button runButton;
    public Button releaseBreakpointButton;
    public Button runWithProfilingButton;
    public Button stepOverButton;
    public Button stepIntoButton;
    public ListView<Instruction> microInstructionList;
    public ListView<String> callStack;
    public Button stopButton;
    private ScriptTabFactory scriptTabFactory;
    private BreakpointService breakpointService;
    private EventService eventService;
    private ScriptViewService scriptViewService;
    private ScriptService scriptService;
    private ExternalFunctionService externalFunctionService;
    private Interpreter interpreter;
    private Breakpoint reachedBreakpoint = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptTabFactory.initializeScriptPane(scriptPane);
        scriptPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                updateMicroInstructionLists(removeExtension(scriptPane.getTabs().get(newValue.intValue()).getText()));
            } else {
                Platform.runLater(() -> microInstructionList.getItems().clear());
            }
        });
    }

    private void updateMicroInstructionLists(String script) {
        try {
            Code code = Optional.ofNullable(externalFunctionService.loadFromCache(script)).orElseGet(() -> {
                return scriptService.read(script, false);
            });
            Platform.runLater(() -> {
                microInstructionList.getItems().clear();
                microInstructionList.getItems().addAll(code.getInstructions());
            });
        } catch (Exception ignored) {
            Platform.runLater(() -> microInstructionList.getItems().clear());
        }
    }

    @Subscribe
    public void onCodeTransletedEvent(CodeTranslatedEvent event) {
        String scriptName = removeExtension(event.getData().getSourceId());
        Optional.ofNullable(scriptPane.getSelectionModel().getSelectedItem()).ifPresent(tab -> {
            if (removeExtension(tab.getText()).equals(scriptName)) {
                updateMicroInstructionLists(scriptName);
            }
        });
    }

    @Subscribe
    public void onScriptChangeEvent(ScriptChangeEvent event) {
        String scriptName = removeExtension(event.getData());
        if (event.getType() == ScriptChangeEvent.Type.DELETED) {
            ScriptContext context = scriptTabFactory.removeFromContext(scriptName);
            if (context != null) {
                Platform.runLater(() -> scriptPane.getTabs().remove(context.getTab()));
            }
        }
        Optional.ofNullable(scriptPane.getSelectionModel().getSelectedItem()).ifPresent(tab -> {
            if (removeExtension(tab.getText()).equals(scriptName)) {
                updateMicroInstructionLists(scriptName);
            }
        });
    }

    public void runWithoutProfiling(ActionEvent actionEvent) {
        onRun(actionEvent, false);
    }

    public void runWithProfiling(ActionEvent actionEvent) {
        onRun(actionEvent, true);
    }

    public void runToCursor(ActionEvent actionEvent) {
        Optional.ofNullable(scriptPane.getSelectionModel().getSelectedItem()).ifPresent(tab -> {
            scriptTabFactory.runWithPause(scriptPane, tab);
        });
    }

    private void onRun(ActionEvent actionEvent, boolean profiling) {
        Optional.ofNullable(scriptPane.getSelectionModel().getSelectedItem()).ifPresent(tab -> {
            scriptTabFactory.runScript(scriptPane, tab, profiling);
        });
    }

    public void newScript(ActionEvent actionEvent) throws IOException {
        scriptViewService.createNewScriptDialog();
    }

    public void stopExecution(ActionEvent actionEvent) {
        eventService.publish(new StopExecutionEvent(this));
    }

    public void resume(ActionEvent actionEvent) {
        scriptTabFactory.resumeExecution();
    }

    public void stepOver(ActionEvent actionEvent) {
        scriptTabFactory.stepOver();
    }

    public void stepInto(ActionEvent actionEvent) {
        scriptTabFactory.stepInto();
    }

    @Subscribe
    public void onExecutionStartedEvent(ExecutionStartedEvent event) {
        runWithProfilingButton.setDisable(true);
        runButton.setDisable(true);
        stopButton.setDisable(false);
    }

    @Subscribe
    public void onExecutionCompletedEvent(ExecutionCompletedEvent event) {
        Platform.runLater(() -> {
            runWithProfilingButton.setDisable(false);
            runButton.setDisable(false);
            stopButton.setDisable(true);
            callStack.getItems().clear();
        });
    }

    @Subscribe
    public void openScript(OpenScriptEvent event) throws IOException {
        String scriptName = removeExtension(event.getData());
        Tab tab = scriptTabFactory.get(scriptName, scriptPane).getTab();
        scriptPane.getSelectionModel().select(tab);
    }

    @Subscribe
    private void onBreakpointReleaseEvent(BreakpointReleaseEvent event) {
        Platform.runLater(() -> {
            releaseBreakpointButton.setDisable(true);
            stepOverButton.setDisable(true);
            stepIntoButton.setDisable(true);
            ScriptContext context = scriptTabFactory.get(reachedBreakpoint.getScriptId(), scriptPane);
            int line = reachedBreakpoint.getLine() - 1;
            context.removeStyle(line, "breakpoint-reached");
            reachedBreakpoint = null;
        });
    }

    @Subscribe
    public void onBreakpointReachedEvent(BreakpointReachedEvent event) {
        Platform.runLater(() -> {
            ScriptContext context = scriptTabFactory.get(event.getData().getScriptId(), scriptPane);
            int line = event.getData().getLine() - 1;
            reachedBreakpoint = event.getData();


            releaseBreakpointButton.setOnMouseClicked(ev -> {
                breakpointService.release(event.getData());
            });

            stepOverButton.setOnMouseClicked(ev -> {
                breakpointService.releaseStepOver(event.getData());
            });

            stepIntoButton.setOnMouseClicked(ev -> {
                breakpointService.releaseStepInto(event.getData());
            });

            releaseBreakpointButton.setDisable(false);
            stepOverButton.setDisable(false);
            stepIntoButton.setDisable(false);
            scriptPane.getSelectionModel().select(context.getTab());
            context.addStyle(line, "breakpoint-reached");
            callStack.getItems().clear();
            List<String> collect = interpreter.callStack()
                    .stream()
                    .filter(code -> code.getSourceId() != null)
                    .map(code -> code.getSourceId() + "@" + code.hashCode())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            callStack.getItems().addAll(collect);

            callStack.refresh();
        });
    }

    @Autowired
    public void setScriptTabFactory(ScriptTabFactory scriptTabFactory) {
        this.scriptTabFactory = scriptTabFactory;
    }

    @Autowired
    public void setBreakpointService(BreakpointService breakpointService) {
        this.breakpointService = breakpointService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setScriptViewService(ScriptViewService scriptViewService) {
        this.scriptViewService = scriptViewService;
    }

    @Autowired
    public void setScriptService(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @Autowired
    public void setExternalFunctionService(ExternalFunctionService externalFunctionService) {
        this.externalFunctionService = externalFunctionService;
    }

    @Autowired
    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }
}
