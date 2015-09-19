package gui;

import gui.config.GuiContext;
import gui.config.ScreensConfiguration;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private ScreensConfiguration screensConfiguration = GuiContext.getScreensConfiguration();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        screensConfiguration.setPrimaryStage(primaryStage);
        screensConfiguration.showScreen((Parent) GuiContext.loadScene("main.fxml"));
    }
}
