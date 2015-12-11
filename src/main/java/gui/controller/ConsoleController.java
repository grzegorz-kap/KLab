package gui.controller;

import com.google.common.eventbus.Subscribe;
import gui.helpers.KeyboardHelper;
import gui.model.CommandHistory;
import interpreter.core.Interpreter;
import interpreter.core.events.PrintEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConsoleController implements Initializable {
    private CommandHistory commandHistory = new CommandHistory();

    @Autowired
    private Interpreter interpreter;

    @FXML
    private CodeArea commandInput;

    @FXML
    private CodeArea consoleOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        String objectName = printEvent.getData().getName();
        if (Objects.nonNull(objectName)) {
            consoleOutput.appendText(String.format("%s = ", objectName));
        }
        consoleOutput.appendText(String.format("%s\n\n", printEvent.getData().toString()));
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
        String command = commandInput.getText();
        commandInput.clear();
        consoleOutput.appendText(String.format(">> %s \n", command));
        commandHistory.add(command);
        keyEvent.consume();

        interpreter.start(command);
    }
}
