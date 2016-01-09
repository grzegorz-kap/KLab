package gui;

import gui.config.GuiContext;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
	private GuiContext guiContext = GuiContext.getInstance();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		guiContext.setPrimaryStage(primaryStage);
		guiContext.showScreen((Parent) guiContext.loadScene("main.fxml"));
	}
}
