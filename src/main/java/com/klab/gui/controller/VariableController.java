package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.interpreter.commons.MemorySpace;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import com.klab.interpreter.types.ObjectData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VariableController {
    private MemorySpace memorySpace;

    @FXML
    private Accordion variablesAccordion;

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
            variablesAccordion.getPanes().clear();
            Stream<ObjectData> list = memorySpace.listCurrentScopeVariables();
            list.forEach(variable -> {
                TitledPane pane = new TitledPane();
                pane.setText(variable.getName());
                Label text = new Label(variable.toString());
                text.setAlignment(Pos.TOP_LEFT);
                pane.setContent(text);
                variablesAccordion.getPanes().add(pane);
            });
        });
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
