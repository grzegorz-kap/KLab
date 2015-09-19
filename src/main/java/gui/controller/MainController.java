package gui.controller;

import gui.config.ScreensConfiguration;
import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainController {

    @Autowired
    private ScreensConfiguration screensConfiguration;


    public void dupa(ActionEvent actionEvent) {

    }
}
