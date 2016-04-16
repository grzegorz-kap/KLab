package com.klab.interpreter.execution.service;

import com.klab.interpreter.execution.model.ExecutionContext;

public interface ExecutionService {
    void start();

    void resetCodeAndStack();

    ExecutionContext getExecutionContext();

    void enableProfiling();

    void disableProfiling();
}
