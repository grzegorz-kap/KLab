package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.execution.model.ExecutionContext;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;

public interface InstructionHandler {
    void handle(InstructionPointer instructionPointer);

    void setExecutionContext(ExecutionContext executionContext);

    InstructionCode getSupportedInstructionCode();
}
