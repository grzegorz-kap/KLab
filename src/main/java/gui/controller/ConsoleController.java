package gui.controller;

import gui.config.ScreensConfiguration;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ConsoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleController.class);

    @Autowired
    private ScreensConfiguration screensConfiguration;

    @FXML
    private TextArea commandInput;

    @FXML
    public void onExit() {
        LOGGER.info(commandInput.getText());
    }
}
