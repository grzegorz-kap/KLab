package com.klab.gui;

import com.klab.gui.config.GuiAppConfiguration;
import com.klab.gui.config.GuiContext;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class App extends Application {
    private ApplicationContext applicationContext = new AnnotationConfigApplicationContext(GuiAppConfiguration.class);
    private GuiContext guiContext = applicationContext.getBean(GuiContext.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        guiContext.setPrimaryStage(primaryStage);
        primaryStage.setTitle("KLab");
        primaryStage.getIcons().add(guiContext.getImage("main.png"));
        primaryStage.setMaximized(true);
        guiContext.showMainScreen();
    }
}
