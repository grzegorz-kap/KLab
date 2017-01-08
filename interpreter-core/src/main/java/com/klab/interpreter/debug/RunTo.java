package com.klab.interpreter.debug;

import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.lexer.model.CodeAddress;

import java.util.Objects;

public class RunTo implements PauseStep {
    private int line;
    private String sourceId;

    public RunTo(int line, String sourceId) {
        this.line = line;
        this.sourceId = Objects.requireNonNull(sourceId);
    }

    @Override
    public boolean shouldStop(InstructionPointer ip) {
        CodeAddress codeAddress = ip.currentInstruction().getCodeAddress();
        return codeAddress != null && codeAddress.getLine() == line && sourceId.equals(ip.getSourceId());
    }
}