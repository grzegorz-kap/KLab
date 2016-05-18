package com.klab.interpreter.debug;

import com.klab.interpreter.execution.model.InstructionPointer;

public interface PauseStep {
    boolean shouldStop(InstructionPointer ip);
}