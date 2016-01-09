package gui.config;

import gui.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class GuiContext {
    private static GuiContext guiContext = new GuiContext();

    private ApplicationContext applicationContext = new AnnotationConfigApplicationContext(GuiAppConfiguration.class);
    private Stage primaryStage;

    private GuiContext() {}

    public static GuiContext getInstance() {
        return guiContext;
    }

    public void showScreen(Parent screen) {
        primaryStage.setScene(new Scene(screen));
        primaryStage.show();
    }

    public Object loadScene(final String url) throws IOException {
        return FXMLLoader.load(App.class.getResource(url), null, null, applicationContext::getBean);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
