package com.klab.gui.factories;

import com.google.common.collect.Maps;
import com.klab.common.EventService;
import com.klab.gui.events.CommandSubmittedEvent;
import com.klab.gui.model.ScriptContext;
import com.klab.gui.service.ScriptViewService;
import com.klab.interpreter.core.code.ScriptFileService;
import com.klab.interpreter.debug.BreakpointService;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptTabFactoryImpl implements ScriptTabFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptTabFactoryImpl.class);

    private ScriptViewService scriptViewService;
    private BreakpointService breakpointService;
    private EventService eventService;
    private ScriptFileService scriptFileService;
    private Map<String, ScriptContext> scripts = Maps.newHashMap();

    @Override
    public ScriptContext create(String scriptName, TabPane scriptPane) {
        ScriptContext context = scripts.get(scriptName);
        if (isNull(context)) {
            context = new ScriptContext(scriptName, scriptViewService.readScript(scriptName));
            context.createLineNumbersFactory(breakpointService);
            context.setOnRunHandler(t -> eventService.publish(new CommandSubmittedEvent(scriptName, this)));
            context.setOnCloseHandler(t -> {
                scriptPane.getTabs().remove(t.getTab());
                scripts.remove(scriptName);
            });
            scripts.put(scriptName, context);
            scriptPane.getTabs().add(context.getTab());
        }
        return context;
    }

    @Override
    public void initializeScriptPane(TabPane scriptPane) {
        scriptPane.setOnKeyPressed(event -> {
            Tab tab = scriptPane.getSelectionModel().getSelectedItem();
            if (tab == null) {
                return;
            }
            if (event.getCode().equals(KeyCode.S) && event.isControlDown()) {
                try {
                    scriptFileService.writeScript(tab.getText(), create(tab.getText(), scriptPane).getText());
                } catch (IOException e) {
                    LOGGER.error("error", e);
                }
            }
            if (event.getCode().equals(KeyCode.F5)) {
                eventService.publish(new CommandSubmittedEvent(tab.getText(), this));
            }
        });
    }

    @Autowired
    public void setScriptViewService(ScriptViewService scriptViewService) {
        this.scriptViewService = scriptViewService;
    }

    @Autowired
    public void setBreakpointService(BreakpointService breakpointService) {
        this.breakpointService = breakpointService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setScriptFileService(ScriptFileService scriptFileService) {
        this.scriptFileService = scriptFileService;
    }
}
