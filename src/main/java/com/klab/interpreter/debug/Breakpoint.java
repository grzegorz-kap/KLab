package com.klab.interpreter.debug;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.translate.model.Instruction;

public interface Breakpoint {
    String getScriptId();

    Integer getLine();

    Integer getColumn();

    boolean isReleased();

    Instruction getInstruction();

    void release();

    void block() throws InterruptedException;

    Code getCode();

    void setCode(Code code);
}
