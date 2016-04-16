package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import com.klab.interpreter.core.events.PrintEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConsoleScriptController {
    @FXML
    private CodeArea consoleOutput;

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
    private void onExecutionStarted(ExecutionStartedEvent event) {
        Platform.runLater(() -> consoleOutput.clear());
    }
}
