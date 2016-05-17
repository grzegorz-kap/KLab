package com.klab.interpreter.execution.service;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.execution.model.ExecutionContext;

import java.util.List;

public interface ExecutionService {
    void start();

    void resetCodeAndStack();

    ExecutionContext getExecutionContext();

    void enableProfiling();

    void disableProfiling();

    List<Code> callStack();
}
