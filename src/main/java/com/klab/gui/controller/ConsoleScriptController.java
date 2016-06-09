package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.interpreter.core.events.ClearConsoleEvent;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import com.klab.interpreter.core.events.PrintEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConsoleScriptController implements Initializable {
    @FXML
    private CodeArea consoleOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        consoleOutput.setDisable(true);
    }

    @Subscribe
    private void onConsoleOut(PrintEvent printEvent) {
        Platform.runLater(() -> {
            if (Objects.nonNull(printEvent.getName())) {
                consoleOutput.appendText(String.format("%s = ", printEvent.getName()));
            }
            consoleOutput.appendText(String.format("%s\n\n", printEvent.getData().toString()));
        });
    }

    @Subscribe
    private void onClearConsoleEvent(ClearConsoleEvent event) {
        Platform.runLater(consoleOutput::clear);
    }

    @Subscribe
    private void onExecutionStarted(ExecutionStartedEvent event) {
        Platform.runLater(consoleOutput::clear);
    }
}
