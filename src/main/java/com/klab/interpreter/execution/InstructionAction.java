package com.klab.interpreter.execution;

import com.klab.interpreter.execution.handlers.InstructionHandler;
import com.klab.interpreter.execution.model.InstructionPointer;

public interface InstructionAction {
    void handle(InstructionHandler handler, InstructionPointer pointer);
}
