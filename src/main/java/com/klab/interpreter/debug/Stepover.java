package com.klab.interpreter.debug;

import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.lexer.model.CodeAddress;

public class StepOver implements PauseStep {
    int callLevel;
    protected int line;

    public StepOver(int callLevel, int line) {
        this.callLevel = callLevel;
        this.line = line;
    }

    @Override
    public boolean shouldStop(InstructionPointer ip) {
        CodeAddress codeAddress = ip.currentInstruction().getCodeAddress();
        return codeAddress != null && (codeAddress.getLine() != line && ip.callLevel() == callLevel || ip.callLevel() < callLevel);
    }
}
