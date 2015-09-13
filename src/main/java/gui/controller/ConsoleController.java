package gui.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConsoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleController.class);

    @FXML
    private CodeArea commandInput;

    @FXML
    private CodeArea consoleOutput;

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
         if(keyEvent.getCode().equals(KeyCode.ENTER)) {
             consoleOutput.appendText(String.format(">> %s\n\n", getInputTextAndClear()));
             keyEvent.consume();
         }
    }

    private String getInputTextAndClear() {
        String inputText = commandInput.getText();
        commandInput.clear();
        return inputText;
    }
}
