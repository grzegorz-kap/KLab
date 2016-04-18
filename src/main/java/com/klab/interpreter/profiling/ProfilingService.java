package com.klab.interpreter.profiling;

import com.klab.interpreter.execution.handlers.InstructionHandler;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.execution.model.InstructionPointer;

import java.util.Collection;

public interface ProfilingService {
    void handle(InstructionHandler handler, InstructionPointer pointer);

    Collection<Code> measured();
}
