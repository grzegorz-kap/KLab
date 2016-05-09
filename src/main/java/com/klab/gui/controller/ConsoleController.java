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
import com.klab.interpreter.core.events.PrintEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.StringUtils;
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
    private Interpreter interpreter;
    private EventService eventService;
    private CommandHistoryService commandHistoryService;
    private CommandHistoryIterator commandHistoryIterator;

    @FXML
    private CodeArea commandInput;

    @FXML
    private CodeArea consoleOutput;

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
