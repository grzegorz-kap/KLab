package gui.config;

import gui.App;
import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class GuiContext {

    private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(GuiAppConfiguration.class);
    private static final GuiContext GUI_CONTEXT = new GuiContext();

    public static Object loadScene(final String url) {
        return GUI_CONTEXT.loadFxml(url);
    }

    public static ScreensConfiguration getScreensConfiguration() {
        return applicationContext.getBean(ScreensConfiguration.class);
    }

    private Object loadFxml(final String fileName) {
        try {
            return FXMLLoader.load(App.class.getResource(fileName), null, null, applicationContext::getBean);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
