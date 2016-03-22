package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VariableController implements Initializable {
    private MemorySpace memorySpace;

    @FXML
    private VBox variablesBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Subscribe
    private void onExecutionComplateEvent(ExecutionCompletedEvent event) {
        refreshVariables();
    }

    @Subscribe
    private void onBreakPointReachedEvent(BreakpointReachedEvent event) {
        refreshVariables();
    }

    private void refreshVariables() {
        Platform.runLater(() -> {
            variablesBox.getChildren().clear();
            memorySpace.listCurrentScopeVariables().forEach(variable -> {
                Label text = new Label(variable.getData().toString());
                text.setAlignment(Pos.TOP_LEFT);

                TitledPane titledPane = new TitledPane(variable.getData().getName(), text);
                titledPane.setExpanded(false);
                variablesBox.getChildren().add(titledPane);
            });
        });
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
