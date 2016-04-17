package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.interpreter.core.events.ExecutionCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Subscribe
    public void onExecutionCompleted(ExecutionCompletedEvent event) {
        LOGGER.info("Execution completed: \n {}", event.getData().getBody());
    }
}
