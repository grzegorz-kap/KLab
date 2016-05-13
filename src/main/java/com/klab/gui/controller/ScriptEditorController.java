package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.common.EventService;
import com.klab.gui.events.CommandSubmittedEvent;
import com.klab.gui.events.OpenScriptEvent;
import com.klab.gui.factories.ScriptTabFactory;
import com.klab.gui.model.ScriptContext;
import com.klab.gui.model.Style;
import com.klab.gui.service.ScriptViewService;
import com.klab.interpreter.core.code.ScriptService;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import com.klab.interpreter.core.events.ScriptChangeEvent;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import com.klab.interpreter.debug.BreakpointService;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.service.functions.external.ExternalFunctionService;
import com.klab.interpreter.translate.model.Instruction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.richtext.StyledTextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptEditorController implements Initializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptEditorController.class);

    private ScriptTabFactory scriptTabFactory;
    private BreakpointService breakpointService;
    private EventService eventService;
    private ScriptViewService scriptViewService;
    private ScriptService scriptService;
    private ExternalFunctionService externalFunctionService;

    // @FXML
    public TabPane scriptPane;
    public Button runButton;
    public Button releaseBreakpointButton;
    public Button runWithProfilingButton;
    public ListView<Instruction> microInstructionList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptTabFactory.initializeScriptPane(scriptPane);
        scriptPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int index = newValue.intValue();
                microInstructionList.getItems().clear();
                if (index != -1) {
                    String script = FilenameUtils.removeExtension(scriptPane.getTabs().get(index).getText());
                    Code instructions = externalFunctionService.loadFunction(script);
                    if (instructions != null) {
                        microInstructionList.getItems().addAll(instructions.getInstructions());
                    }
                    Code code = scriptService.getCode(script);
                    microInstructionList.getItems().addAll(code.getInstructions());
                }
            } catch (Exception ignored) {
                LOGGER.error("Error loading microcode");
            }
        });

        externalFunctionService.addLoadListener(externalFunction -> {
            Tab tab = scriptPane.getSelectionModel().getSelectedItem();
            if (tab != null && tab.getText().equals(externalFunction.getName())) {
                microInstructionList.getItems().clear();
                microInstructionList.getItems().addAll(externalFunction.getCode().getInstructions());
            }
        });

    }

    public void runWithoutProfiling(ActionEvent actionEvent) {
        onRun(actionEvent, false);
    }

    public void runWithProfiling(ActionEvent actionEvent) {
        onRun(actionEvent, true);
    }

    private void onRun(ActionEvent actionEvent, boolean profiling) {
        Tab tab = scriptPane.getSelectionModel().getSelectedItem();
        if (tab != null) {
            CommandSubmittedEvent event = CommandSubmittedEvent.create()
                    .data(tab.getText())
                    .profiling(profiling)
                    .build(this);
            eventService.publish(event);
        }
    }

    public void newScript(ActionEvent actionEvent) throws IOException {
        scriptViewService.createNewScriptDialog();
    }

    @Subscribe
    public void onExecutionStartedEvent(ExecutionStartedEvent event) {
        runWithProfilingButton.setDisable(true);
        runButton.setDisable(true);
    }

    @Subscribe
    public void onExecutionCompletedEvent(ExecutionCompletedEvent event) {
        runWithProfilingButton.setDisable(false);
        runButton.setDisable(false);
    }

    @Subscribe
    public void openScript(OpenScriptEvent event) throws IOException {
        String scriptName = FilenameUtils.removeExtension(event.getData());
        Tab tab = scriptTabFactory.get(scriptName, scriptPane).getTab();
        scriptPane.getSelectionModel().select(tab);
    }

    @Subscribe
    public void onScriptChangeEvent(ScriptChangeEvent event) {
        if (event.getType() == ScriptChangeEvent.Type.DELETED) {
            String name = FilenameUtils.removeExtension(event.getData());
            ScriptContext context = scriptTabFactory.removeFromContext(name);
            if (context != null) {
                Platform.runLater(() -> scriptPane.getTabs().remove(context.getTab()));
            }
        }
    }

    @Subscribe
    public void onBreakpointReachedEvent(BreakpointReachedEvent event) {
        Platform.runLater(() -> {
            ScriptContext context = scriptTabFactory.get(event.getData().getSourceId(), scriptPane);
            int line = event.getData().getAddress().getLine() - 1;
            releaseBreakpointButton.setOnMouseClicked(ev -> {
                breakpointService.release(event.getData());
                releaseBreakpointButton.setDisable(true);
                context.getCodeArea().clearStyle(line);
            });

            releaseBreakpointButton.setDisable(false);
            scriptPane.getSelectionModel().select(context.getTab());
            StyledTextArea<Style> area = context.getCodeArea();
            area.setStyle(line, () -> "-fx-background-fill: yellow");
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
}
