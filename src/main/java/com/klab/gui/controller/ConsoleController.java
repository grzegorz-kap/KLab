package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.common.EventService;
import com.klab.gui.events.AppendCommandEvent;
import com.klab.gui.events.CommandSubmittedEvent;
import com.klab.gui.helpers.KeyboardHelper;
import com.klab.gui.model.CommandHistoryIterator;
import com.klab.gui.service.CommandHistoryService;
import com.klab.interpreter.core.ExecutionCommand;
import com.klab.interpreter.core.Interpreter;
import com.klab.interpreter.core.events.ClearConsoleEvent;
import com.klab.interpreter.core.events.ErrorEvent;
import com.klab.interpreter.core.events.PrintEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConsoleController implements InitializingBean {
    public TextArea commandInput;
    public CodeArea consoleOutput;
    private Interpreter interpreter;
    private EventService eventService;
    private CommandHistoryService commandHistoryService;
    private CommandHistoryIterator commandHistoryIterator;

    @Override
    public void afterPropertiesSet() throws Exception {
        commandHistoryIterator = commandHistoryService.iterator();
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (KeyboardHelper.isEnterPressed(keyEvent)) {
            onEnter(keyEvent);
        } else if (KeyboardHelper.isArrowUpPressed(keyEvent)) {
            onArrowUp(keyEvent);
        } else if (KeyboardHelper.isArrowDownPressed(keyEvent)) {
            onArrowDown(keyEvent);
        } else if (KeyboardHelper.isEnterShift(keyEvent)) {
            commandInput.appendText("\n");
        }
    }

    @Subscribe
    public void onError(ErrorEvent event) {
        Platform.runLater(() -> {
            consoleOutput.appendText("\n" + event.getData().getAddress());
            consoleOutput.appendText("\n" + event.getData().getMessage() + "\n");
            consoleOutput.appendText(ExceptionUtils.getStackTrace(event.getData()));
            consoleOutput.appendText("\n");
        });
    }

    @Subscribe
    public void onPrintEvent(PrintEvent printEvent) {
        Platform.runLater(() -> {
            String objectName = printEvent.getName();
            if (Objects.nonNull(objectName)) {
                consoleOutput.appendText(String.format("%s = ", objectName));
            }
            consoleOutput.appendText(String.format("%s\n\n", printEvent.getData().toString()));
        });
    }

    @Subscribe
    public void onCommandSubmittedEvent(CommandSubmittedEvent command) {
        consoleOutput.appendText(String.format(">> %s \n", command.getData()));
        commandHistoryService.add(command.getData());
        interpreter.startAsync(new ExecutionCommand(command.getData(), command.isProfiling()));
    }

    @Subscribe
    public void onCommandAppendEvent(AppendCommandEvent event) {
        commandInput.appendText(StringUtils.appendIfMissing(event.getData(), "\n"));
    }

    @Subscribe
    public void onClearConsoleEvent(ClearConsoleEvent event) {
        Platform.runLater(consoleOutput::clear);
    }

    private void onArrowDown(KeyEvent keyEvent) {
        commandInput.clear();
        commandInput.appendText(commandHistoryIterator.prev());
        keyEvent.consume();
    }

    private void onArrowUp(KeyEvent keyEvent) {
        commandInput.clear();
        commandInput.appendText(commandHistoryIterator.next());
        keyEvent.consume();
    }

    private void onEnter(KeyEvent keyEvent) {
        CommandSubmittedEvent event = CommandSubmittedEvent.create()
                .data(commandInput.getText())
                .profiling(false)
                .build(this);
        eventService.publish(event);
        commandInput.clear();
        keyEvent.consume();
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Autowired
    public void setCommandHistoryService(CommandHistoryService commandHistoryService) {
        this.commandHistoryService = commandHistoryService;
    }
}
