package com.klab.interpreter.debug;

import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.translate.model.Instruction;

public class StepOver {
    private int line;

    public StepOver(int line) {
        this.line = line;
    }

    public boolean isBreakpoint(Instruction instruction) {
        CodeAddress codeAddress = instruction.getCodeAddress();
        return codeAddress != null && codeAddress.getLine() != this.line;
    }
}
