package interpreter.service.functions.external;

import com.google.common.eventbus.Subscribe;
import interpreter.core.ScriptFileService;
import interpreter.core.events.ScriptChangeEvent;
import interpreter.service.functions.model.CallInstruction;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;
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
public class ExternalFunctionServiceImpl implements ExternalFunctionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalFunctionServiceImpl.class);
    private ScriptFileService scriptFileService;
    private ExternalFunctionParser externalFunctionParser;
    private Map<FunctionKey, ExternalFunction> functionsCache = Collections.synchronizedMap(new HashMap<>());

    @Override
    public ExternalFunction loadFunction(CallInstruction cl) {
        FunctionKey key = new FunctionKey(cl.getName(), cl.getArgumentsNumber());
        ExternalFunction externalFunction = functionsCache.get(key);
        return externalFunction != null ? externalFunction : read(key);
    }

    private ExternalFunction read(FunctionKey key) {
        try {
            String fileContent = scriptFileService.readScript(key.name);
            ExternalFunction e = externalFunctionParser.parse(fileContent);
            e.getCode().add(new Instruction(InstructionCode.FUNCTION_END, 0));
            functionsCache.put(key, e);
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

    @Autowired
    public void setScriptFileService(ScriptFileService scriptFileService) {
        this.scriptFileService = scriptFileService;
    }

    @Autowired
    public void setExternalFunctionParser(ExternalFunctionParser externalFunctionParser) {
        this.externalFunctionParser = externalFunctionParser;
    }

    private static class FunctionKey {
        public String name;
        public int arguments;

        public FunctionKey(String name, int arguments) {
            this.name = name;
            this.arguments = arguments;
        }

        @Override
        public boolean equals(Object obj) {
            FunctionKey key = (FunctionKey) obj;
            return key.arguments == this.arguments && key.name.equals(this.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
