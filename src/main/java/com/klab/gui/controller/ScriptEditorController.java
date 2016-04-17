package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.common.EventService;
import com.klab.gui.events.CommandSubmittedEvent;
import com.klab.gui.events.OpenScriptEvent;
import com.klab.gui.factories.ScriptTabFactory;
import com.klab.gui.model.ScriptContext;
import com.klab.gui.model.Style;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import com.klab.interpreter.debug.BreakpointService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.richtext.StyledTextArea;
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
    private ScriptTabFactory scriptTabFactory;
    private BreakpointService breakpointService;
    private EventService eventService;

    @FXML
    private TabPane scriptPane;

    @FXML
    private Button releaseBreakpointButton;

    @FXML
    private Button runWithProfilingButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scriptTabFactory.initializeScriptPane(scriptPane);
    }

    @FXML
    public void runWithProfiling(ActionEvent actionEvent) {
        Tab tab = scriptPane.getSelectionModel().getSelectedItem();
        if (tab != null) {
            ScriptContext context = scriptTabFactory.get(tab.getText(), this.scriptPane);
            if (context != null) {
                CommandSubmittedEvent event = CommandSubmittedEvent.create()
                        .data(context.getText())
                        .profiling(true)
                        .build(this);
                eventService.publish(event);
            }
        }
    }

    @Subscribe
    public void onExecutionStartedEvent(ExecutionStartedEvent event) {
        runWithProfilingButton.setDisable(true);
    }

    @Subscribe
    public void onExecutionCompletedEvent(ExecutionCompletedEvent event) {
        runWithProfilingButton.setDisable(false);
    }

    @Subscribe
    public void openScript(OpenScriptEvent event) throws IOException {
        String scriptName = FilenameUtils.removeExtension(event.getData());
        Tab tab = scriptTabFactory.get(scriptName, scriptPane).getTab();
        scriptPane.getSelectionModel().select(tab);
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
            area.setStyle(line, () -> "-fx-font-weight: bold");
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
}
