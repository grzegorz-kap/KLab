package com.klab.gui.controller;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.klab.gui.model.Variable;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.commons.memory.ObjectWrapper;
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
import java.util.Map;
import java.util.ResourceBundle;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VariableController implements Initializable {
    private MemorySpace memorySpace;
    private int currentScope = Integer.MIN_VALUE;
    private Map<ObjectWrapper, Variable<TitledPane>> variablesMap = Maps.newHashMap();

    @FXML
    private VBox variablesBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Subscribe
    private void onExecutionCompleteEvent(ExecutionCompletedEvent event) {
        Platform.runLater(this::refreshVariables);
    }

    @Subscribe
    private void onBreakPointReachedEvent(BreakpointReachedEvent event) {
        Platform.runLater(this::refreshVariables);
    }

    private void refreshVariables() {
        if (memorySpace.scopeId() != currentScope) {
            variablesBox.getChildren().clear();
            variablesMap.clear();
        }

        memorySpace.listCurrentScopeVariables()
                .filter(this::isUpdated)
                .peek(this::removeUpdated)
                .map(this::createNew)
                .peek(var -> variablesBox.getChildren().add(var.getNode()))
                .forEach(var -> variablesMap.put(var.getObjectWrapper(), var));
        currentScope = memorySpace.scopeId();
    }

    private Variable<TitledPane> createNew(ObjectWrapper variable) {
        Label text = new Label(variable.getData().toString());
        text.setAlignment(Pos.TOP_LEFT);
        TitledPane titledPane = new TitledPane(variable.getData().getName(), text);
        titledPane.setExpanded(false);
        return new Variable<>(titledPane, variable);
    }

    private void removeUpdated(ObjectWrapper variable) {
        Variable<TitledPane> var = variablesMap.remove(variable);
        if (var != null) {
            variablesBox.getChildren().remove(var.getNode());
        }
    }

    private boolean isUpdated(ObjectWrapper w) {
        Variable<TitledPane> variable = variablesMap.get(w);
        return variable == null || variable.getVersion() != w.getVersion();
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
