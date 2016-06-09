package com.klab.gui.factories;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.Runnables;
import com.klab.common.EventService;
import com.klab.gui.events.CommandSubmittedEvent;
import com.klab.gui.model.ScriptContext;
import com.klab.gui.service.ScriptViewService;
import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.core.code.ScriptFileService;
import com.klab.interpreter.core.events.StopExecutionEvent;
import com.klab.interpreter.debug.*;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.Sizeable;
import com.klab.interpreter.types.scalar.Scalar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import org.apache.commons.lang3.StringUtils;
import org.fxmisc.richtext.StyleClassedTextArea;
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

    private IdentifierMapper identifierMapper;
    private MemorySpace memorySpace;
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
            context.setTooltipProducer(this::handleVariableTooltip);
            scripts.put(scriptName, context);
            scriptPane.getTabs().add(context.getTab());
        }
        return context;
    }

    private String handleVariableTooltip(String name) {
        Integer address = identifierMapper.getMainAddress(name);
        if (address != null) {
            ObjectData data = memorySpace.get(address);
            if (data != null && StringUtils.isNoneBlank(data.getName())) {
                String result = String.format("%s = ", data.getName());
                if (data instanceof Scalar) {
                    return result + data.toString();
                }
                if (data instanceof Sizeable) {
                    Sizeable sizeable = (Sizeable) data;
                    if (sizeable.length() <= 5) {
                        return result + "\n" + data.toString();
                    } else {
                        return String.format("%s <%d x %d>", result, sizeable.getRows(), sizeable.getCells());
                    }
                }
                return result + data.toString();
            }
        }
        return "";
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
                resumeExecution();
            } else if (event.getCode() == KeyCode.F8) {
                stepOver();
            } else if (event.getCode() == KeyCode.F7) {
                stepInto();
            }

            if (tab != null) {
                if (event.getCode().equals(KeyCode.S) && event.isControlDown()) {
                    writeScript(scriptPane, tab);
                } else if (event.getCode().equals(KeyCode.F5)) {
                    runScript(scriptPane, tab, false);
                } else if (event.getCode() == KeyCode.F5 && event.isShiftDown()) {
                    runScript(scriptPane, tab, true);
                } else if (event.getCode() == KeyCode.F3) {
                    runWithPause(scriptPane, tab);
                }
            }
        });
    }

    @Override
    public void stepInto() {
        stepIntoAction.run();
    }

    @Override
    public void stepOver() {
        stepOverAction.run();
    }

    @Override
    public void resumeExecution() {
        resumeExecution.run();
    }

    @Override
    public void runWithPause(TabPane scriptPane, Tab tab) {
        ScriptContext scriptContext = get(tab.getText(), scriptPane, true);
        if (scriptContext != null) {
            StyleClassedTextArea codeArea = scriptContext.getCodeArea();
            int currentParagraph = codeArea.getCurrentParagraph();
            eventService.publish(new RunToEvent(scriptContext.getScriptId(), currentParagraph + 1, this));
            runScript(scriptPane, tab, false);
        }
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

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
