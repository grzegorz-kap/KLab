package gui.controller;

import com.google.common.eventbus.Subscribe;
import common.EventService;
import gui.events.CommandSubmittedEvent;
import gui.helpers.KeyboardHelper;
import gui.model.CommandHistory;
import interpreter.core.Interpreter;
import interpreter.core.events.PrintEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConsoleController {
    private CommandHistory commandHistory = new CommandHistory();
    private Interpreter interpreter;
    private EventService eventService;

    @FXML
    private CodeArea commandInput;

    @FXML
    private CodeArea consoleOutput;

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
        String objectName = printEvent.getData().getName();
        if (Objects.nonNull(objectName)) {
            consoleOutput.appendText(String.format("%s = ", objectName));
        }
        consoleOutput.appendText(String.format("%s\n\n", printEvent.getData().toString()));
    }

    @Subscribe
    public void onCommandSubmittedEvent(CommandSubmittedEvent command) {
        consoleOutput.appendText(String.format(">> %s \n", command.getData()));
        commandHistory.add(command.getData());
        interpreter.start(command.getData());
    }

    private void onArrowDown(KeyEvent keyEvent) {
        commandInput.clear();
        commandInput.appendText(commandHistory.prev());
        keyEvent.consume();
    }

    private void onArrowUp(KeyEvent keyEvent) {
        commandInput.clear();
        commandInput.appendText(commandHistory.next());
        keyEvent.consume();
    }

    private void onEnter(KeyEvent keyEvent) {
        eventService.publish(new CommandSubmittedEvent(commandInput.getText(), this));
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
}
