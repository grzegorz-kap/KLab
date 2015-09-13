package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KLabGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/main.fxml"));
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setTitle("KLab");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
