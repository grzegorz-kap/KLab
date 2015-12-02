package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import gui.helpers.KeyboardHelper;
import gui.model.CommandHistory;
import gui.view.CommandHistoryViewService;
import gui.view.ConsoleViewService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConsoleController implements Initializable {
    private ConsoleViewService consoleViewService;
    private CommandHistoryViewService commandHistoryViewService;

    private KeyboardHelper keyboardHelper;
    private CommandHistory commandHistory = new CommandHistory();

    @FXML
    private CodeArea commandInput;

    @FXML
    private CodeArea consoleOutput;

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyboardHelper.isEnterPressed(keyEvent)) {
            onEnter(keyEvent);
        } else if (keyboardHelper.isArrowUpPressed(keyEvent)) {
            onArrowUp(keyEvent);
        } else if (keyboardHelper.isArrowDownPressed(keyEvent)) {
            onArrowDown(keyEvent);
        } else if (keyboardHelper.isEnterShift(keyEvent)) {
            commandInput.appendText("\n");
        }
    }

    private void onArrowDown(KeyEvent keyEvent) {
        commandHistoryViewService.onArrowDown();
        keyEvent.consume();
    }

    private void onArrowUp(KeyEvent keyEvent) {
        commandHistoryViewService.onArrowUp();
        keyEvent.consume();
    }

    private void onEnter(KeyEvent keyEvent) {
        commandHistoryViewService.onCommandSubmit();
        consoleViewService.onCommandSubmit();
        keyEvent.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        consoleViewService.setCommandInput(commandInput);
        consoleViewService.setConsoleOutput(consoleOutput);
        commandHistoryViewService.setCommandHistory(commandHistory);
        commandHistoryViewService.setCommandInput(commandInput);
    }

    @Autowired
    public void setConsoleViewService(ConsoleViewService consoleViewService) {
        this.consoleViewService = consoleViewService;
    }

    @Autowired
    public void setKeyboardHelper(KeyboardHelper keyboardHelper) {
        this.keyboardHelper = keyboardHelper;
    }

    @Autowired
    public void setCommandHistoryViewService(CommandHistoryViewService commandHistoryViewService) {
        this.commandHistoryViewService = commandHistoryViewService;
    }
}
