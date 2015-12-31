package interpreter.core;

import com.google.common.eventbus.Subscribe;
import interpreter.core.events.ScriptChangeEvent;
import interpreter.execution.model.Code;
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
import java.util.Objects;

@Service
public class ScriptServiceImpl implements ScriptService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptServiceImpl.class);
    private Map<String, Code> cachedCode = Collections.synchronizedMap(new HashMap<>());
    private CodeGenerator codeGenerator;
    private ScriptFileService scriptFileService;

    @Subscribe
    public void onScriptChange(ScriptChangeEvent event) {
        boolean removed = cachedCode.remove(FilenameUtils.removeExtension(event.getData())) != null;
        if (removed) {
            LOGGER.info("Script '{}' removed from cache, cause: '{}'", event.getData(), event.getType());
        }
    }

    @Override
    public Code getCode(String scriptName) {
        Code code = cachedCode.get(scriptName);
        return Objects.nonNull(code) ? code : read(scriptName);
    }

    private Code read(String scriptName) {
        Code code = null;
        try {
            code = codeGenerator.translate(scriptFileService.readScript(scriptName));
            code.add(new Instruction(InstructionCode.SCRIPT_EXIT, 0));
            cachedCode.put(scriptName, code);
        } catch (IOException e) {
            LOGGER.error("Error reading script: '{}', cause: '{}", scriptName);
        }
        return code;
    }

    @Autowired
    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @Autowired
    public void setScriptFileService(ScriptFileService scriptFileService) {
        this.scriptFileService = scriptFileService;
    }
}
