package interpreter.service.functions.external;

import com.google.common.eventbus.Subscribe;
import interpreter.core.code.ScriptFileService;
import interpreter.core.events.ScriptChangeEvent;
import interpreter.debug.BreakpointService;
import interpreter.debug.BreakpointUpdatedEvent;
import interpreter.service.functions.model.CallInstruction;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
class ExternalFunctionServiceImpl implements ExternalFunctionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalFunctionServiceImpl.class);
    private ScriptFileService scriptFileService;
    private BreakpointService breakpointService;
    private ExternalFunctionParser externalFunctionParser;
    private Map<FunctionMapKey, ExternalFunction> functionsCache = Collections.synchronizedMap(new HashMap<>());

    @Override
    public ExternalFunction loadFunction(CallInstruction cl) {
        FunctionMapKey key = new FunctionMapKey(cl.getName(), cl.getArgumentsNumber(), cl.getExpectedOutputSize());
        ExternalFunction externalFunction = functionsCache.get(key);
        return externalFunction != null ? externalFunction : read(key, cl);
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
            LOGGER.info("Function \n{}", e);
            return e;
        } catch (IOException e) {
            LOGGER.error("Error reading function '{}'. {}", key.name, e);
        }
        throw new UnsupportedOperationException(); // TODO execption
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
                .filter(fun -> fun.getName().equals(event.getData().getSourceId()))
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
}
