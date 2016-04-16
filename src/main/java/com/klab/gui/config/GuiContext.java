package com.klab.gui.config;

import com.google.common.eventbus.Subscribe;
import com.klab.gui.App;
import com.klab.gui.events.OpenScriptEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GuiContext implements ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;
    private Stage primaryStage;
    private Stage editorStage;

    public void showMainScreen() throws IOException {
        showScreen((Parent) loadScene("main.fxml"));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        (editorStage = new Stage()).setScene(new Scene((Parent) loadScene("script-editor.fxml")));
    }

    @Subscribe
    public void showEditorScreen(OpenScriptEvent event) throws IOException {
        editorStage.show();
        editorStage.toFront();
    }

    private void showScreen(Parent screen) {
        primaryStage.setScene(new Scene(screen));
        primaryStage.show();
    }

    private Object loadScene(final String url) throws IOException {
        return FXMLLoader.load(App.class.getResource(url), null, null, applicationContext::getBean);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
