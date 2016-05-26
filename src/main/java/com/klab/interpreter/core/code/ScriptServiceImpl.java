package com.klab.interpreter.core.code;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import com.klab.interpreter.core.events.ScriptChangeEvent;
import com.klab.interpreter.debug.BreakpointService;
import com.klab.interpreter.debug.BreakpointUpdatedEvent;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
class ScriptServiceImpl implements ScriptService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptServiceImpl.class);
    private Map<String, Code> cachedCode = Maps.newHashMap();
    private CodeGenerator codeGenerator;
    private ScriptFileService scriptFileService;
    private BreakpointService breakpointService;

    @Override
    public Code getCode(String scriptName) {
        return Optional.ofNullable(cachedCode.get(scriptName)).orElse(read(scriptName));
    }

    @Subscribe
    public void onExecutionStart(ExecutionStartedEvent executionStartedEvent) {
        cachedCode.values().stream()
                .forEach(code -> code.forEach(instruction -> instruction.setProfilingData(null)));
    }

    @Subscribe
    public void onScriptChange(ScriptChangeEvent event) {
        boolean removed = cachedCode.remove(FilenameUtils.removeExtension(event.getData())) != null;
        if (removed) {
            LOGGER.info("Script '{}' removed from cache, cause: '{}'", event.getData(), event.getType());
        }
    }

    @Subscribe
    public void onBreakpointsUpdated(BreakpointUpdatedEvent event) {
        Code code = cachedCode.get(event.getData().getScriptId());
        if (nonNull(code)) {
            breakpointService.updateBreakpoints(code);
        }
    }

    private Code read(String scriptName) {
        Code code = null;
        try {
            code = codeGenerator.translate(scriptFileService.readScript(scriptName));
            code.setSourceId(scriptName);
            code.add(new Instruction(InstructionCode.SCRIPT_EXIT, 0), null);
            breakpointService.updateBreakpoints(code);
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

    @Autowired
    public void setBreakpointService(BreakpointService breakpointService) {
        this.breakpointService = breakpointService;
    }
}