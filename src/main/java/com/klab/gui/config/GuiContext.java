package com.klab.gui.config;

import com.klab.gui.App;
import com.klab.gui.CustomInitializeble;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

@Component
public class GuiContext implements ApplicationContextAware, InitializingBean, ResourceLoaderAware {
    private ApplicationContext applicationContext;
    private ResourceLoader resourceLoader;
    private Stage primaryStage = new Stage();
    private Stage editorStage = new Stage();
    private Stage profilingStage = new Stage();

    public void showMainScreen() throws IOException {
        showScreen(loadScene("main.fxml"));
    }

    public Image getImage(String name) throws IOException {
        try (InputStream inputStream = resourceLoader.getResource("classpath:/img/" + name).getInputStream()) {
            return new Image(inputStream);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        editorStage.setScene(new Scene(loadScene("script-editor.fxml")));
        profilingStage.setScene(new Scene(loadScene("profiling.fxml")));

        editorStage.setTitle("KLab - script editor");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void showScriptEditor() {
        editorStage.setMaximized(true);
        showToFront(editorStage);
    }

    public void showProfilingStage() {
        showToFront(profilingStage);
    }

    private void showToFront(Stage stage) {
        stage.show();
        stage.toFront();
    }

    private void showScreen(Parent screen) {
        primaryStage.setScene(new Scene(screen));
        primaryStage.show();
    }

    private <T> T loadScene(final String url) {
        return loadScene(url, null);
    }

    public <T, U> T loadScene(final String url, Consumer<U> controllerConsumer) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url), null, null, applicationContext::getBean);
        try {
            T load = loader.load();
            U controller = loader.getController();
            if (controllerConsumer != null) {
                controllerConsumer.accept(controller);
            }
            if (controller instanceof CustomInitializeble) {
                ((CustomInitializeble) controller).customInit();
            }
            return load;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
