package com.klab.gui.factories;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.Runnables;
import com.klab.common.EventService;
import com.klab.gui.events.CommandSubmittedEvent;
import com.klab.gui.model.ScriptContext;
import com.klab.gui.service.ScriptViewService;
import com.klab.interpreter.core.code.ScriptFileService;
import com.klab.interpreter.core.events.StopExecutionEvent;
import com.klab.interpreter.debug.Breakpoint;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import com.klab.interpreter.debug.BreakpointReleaseEvent;
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
    private Runnable resumeExecution = Runnables.doNothing();
    private Runnable stepOverAction = Runnables.doNothing();
    private Runnable stepIntoAction = Runnables.doNothing();

    @Override
    public ScriptContext get(String scriptName, TabPane scriptPane) {
        return get(scriptName, scriptPane, false);
    }

    @Override
    public ScriptContext get(String scriptName, TabPane scriptPane, boolean noCreate) {
        ScriptContext context = scripts.get(scriptName);
        if (isNull(context) && !noCreate) {
            context = new ScriptContext(scriptName, scriptViewService.readScript(scriptName));
            context.createLineNumbersFactory(breakpointService);
            context.setOnRunHandler(t -> eventService.publish(CommandSubmittedEvent.create().data(scriptName).build(this)));
            context.setOnCloseHandler(t -> {
                scriptPane.getTabs().remove(t.getTab());
                scripts.remove(scriptName);
            });
            context.setOnDeleteHandler(this::handleOnDelete);
            context.setOnRenameHandler(this::handleOnRename);
            scripts.put(scriptName, context);
            scriptPane.getTabs().add(context.getTab());
        }
        return context;
    }

    @Override
    public ScriptContext removeFromContext(String data) {
        return scripts.remove(data);
    }

    private void handleOnDelete(ScriptContext scriptContext) {
        try {
            scriptViewService.deleteScript(scriptContext.getScriptId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleOnRename(ScriptContext scriptContext) {
        try {
            scriptViewService.renameScript(scriptContext.getScriptId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Subscribe
    private void onStopExecution(StopExecutionEvent event) {
        scripts.values().stream()
                .map(ScriptContext::getCodeArea)
                .forEach(codeArea -> codeArea.clearStyle(0, codeArea.getText().length()));
    }

    @Subscribe
    private void onBreakpointEvent(BreakpointReachedEvent event) {
        Breakpoint data = event.getData();
        resumeExecution = () -> {
            breakpointService.release(data);
        };
        stepOverAction = () -> {
            breakpointService.releaseStepOver(data);
        };
        stepIntoAction = () -> {
            breakpointService.releaseStepInto(data);
        };
    }

    @Subscribe
    private void onBreakpointRelease(BreakpointReleaseEvent event) {
        resumeExecution = Runnables.doNothing();
        stepOverAction = Runnables.doNothing();
        stepIntoAction = Runnables.doNothing();
    }

    @Override
    public void initializeScriptPane(TabPane scriptPane) {
        scriptPane.setOnKeyPressed(event -> {
            Tab tab = scriptPane.getSelectionModel().getSelectedItem();
            if (event.getCode() == KeyCode.F2 && event.isControlDown()) {
                eventService.publish(new StopExecutionEvent(this));
            } else if (event.getCode() == KeyCode.F9) {
                resumeExecution.run();
            } else if (event.getCode() == KeyCode.F8) {
                stepOverAction.run();
            } else if (event.getCode() == KeyCode.F7) {
                stepIntoAction.run();
            }

            if (tab != null) {
                if (event.getCode().equals(KeyCode.S) && event.isControlDown()) {
                    writeScript(scriptPane, tab);
                } else if (event.getCode().equals(KeyCode.F5)) {
                    runScript(scriptPane, tab, false);
                } else if (event.getCode() == KeyCode.F9 && event.isShiftDown()) {
                    runScript(scriptPane, tab, true);
                }
            }
        });
    }

    private void runScript(TabPane scriptPane, Tab tab, boolean profiling) {
        writeScript(scriptPane, tab);
        eventService.publish(CommandSubmittedEvent.create()
                .data(tab.getText())
                .profiling(profiling)
                .build(this)
        );
    }

    private void writeScript(TabPane scriptPane, Tab tab) {
        try {
            scriptFileService.writeScript(tab.getText(), get(tab.getText(), scriptPane).getText());
        } catch (IOException e) {
            LOGGER.error("error", e);
        }
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
