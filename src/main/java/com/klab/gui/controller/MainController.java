package com.klab.gui.controller;

import com.google.common.eventbus.Subscribe;
import com.klab.gui.config.GuiContext;
import com.klab.gui.events.OpenScriptEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private GuiContext guiContext;

    @Subscribe
    public void showEditorScreen(OpenScriptEvent event) throws IOException {
        guiContext.showScriptEditor();
    }

    @Autowired
    public void setGuiContext(GuiContext guiContext) {
        this.guiContext = guiContext;
    }
}
