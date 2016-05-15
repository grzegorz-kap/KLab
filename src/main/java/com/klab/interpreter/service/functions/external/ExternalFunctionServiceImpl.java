package com.klab.interpreter.service.functions.external;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.klab.interpreter.core.code.ScriptFileService;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import com.klab.interpreter.core.events.ScriptChangeEvent;
import com.klab.interpreter.debug.BreakpointService;
import com.klab.interpreter.debug.BreakpointUpdatedEvent;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.service.functions.model.CallInstruction;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
class ExternalFunctionServiceImpl implements ExternalFunctionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalFunctionServiceImpl.class);
    private ScriptFileService scriptFileService;
    private BreakpointService breakpointService;
    private ExternalFunctionParser externalFunctionParser;
    private Map<FunctionMapKey, ExternalFunction> functionsCache = Maps.newHashMap();
    private List<Consumer<ExternalFunction>> loadListeners = Lists.newArrayList();
    private TaskExecutor taskExecutor;

    @Override
    public ExternalFunction loadFunction(CallInstruction cl) {
        FunctionMapKey key = new FunctionMapKey(cl.getName(), cl.getArgumentsNumber(), cl.getExpectedOutputSize());
        ExternalFunction externalFunction = functionsCache.get(key);
        return externalFunction != null ? externalFunction : read(key, cl);
    }

    @Override
    public Code loadFunction(String name) {
        Map.Entry<FunctionMapKey, ExternalFunction> entry = functionsCache.entrySet().stream()
                .filter(e -> e.getKey().name.equals(name)).findFirst().orElse(null);
        if (entry != null) {
            return entry.getValue().getCode();
        } else {
            return null;
        }
    }

    private ExternalFunction read(FunctionMapKey key, CallInstruction cl) {
        try {
            String fileContent = scriptFileService.readScript(key.name);
            ExternalFunction e = externalFunctionParser.parse(fileContent);
            EndFunctionInstruction ret = new EndFunctionInstruction(e.getArguments().size(), e.getReturns().size());
            ret.setExpectedOutput(cl.getExpectedOutputSize());
            e.getCode().add(ret, null);
            e.getCode().setSourceId(key.name);
            breakpointService.updateBreakpoints(e.getCode());
            functionsCache.put(key, e);
            taskExecutor.execute(() -> loadListeners.forEach(c -> c.accept(e)));
            return e;
        } catch (IOException e) {
            LOGGER.error("Error reading function '{}'. {}", key.name, e);
        }
        throw new UnsupportedOperationException(); // TODO execption
    }

    @Override
    public void addLoadListener(Consumer<ExternalFunction> listener) {
        loadListeners.add(listener);
    }

    @Subscribe
    public void onExecutionStartEvent(ExecutionStartedEvent executionStartedEvent) {
        for (ExternalFunction externalFunction : functionsCache.values()) {
            externalFunction.getCode().forEach(instruction -> instruction.setProfilingData(null));
        }
    }

    @Subscribe
    public void onScriptChangedEvent(ScriptChangeEvent event) {
        String name = FilenameUtils.removeExtension(event.getData());
        boolean result = functionsCache.entrySet().removeIf(entry -> entry.getKey().name.equals(name));
        if (result) {
            LOGGER.info("Function '{}' removed from cache, cause: {}", name, event.getType());
        }
    }

    @Subscribe
    public void onBreakpointListChanged(BreakpointUpdatedEvent event) {
        functionsCache.values().stream()
                .filter(fun -> fun.getName().equals(event.getData().getScriptId()))
                .forEach(fun -> breakpointService.updateBreakpoints(fun.getCode()));
    }

    @Autowired
    public void setScriptFileService(ScriptFileService scriptFileService) {
        this.scriptFileService = scriptFileService;
    }

    @Autowired
    public void setExternalFunctionParser(ExternalFunctionParser externalFunctionParser) {
        this.externalFunctionParser = externalFunctionParser;
    }

    @Autowired
    public void setBreakpointService(BreakpointService breakpointService) {
        this.breakpointService = breakpointService;
    }

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}
