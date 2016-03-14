package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.interpreter.commons.MemorySpace;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VariableController {
    private MemorySpace memorySpace;

    @FXML
    private Accordion variablesAccordion;

    @Subscribe
    private void onBreakPointReachedEvent(BreakpointReachedEvent event) {
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
