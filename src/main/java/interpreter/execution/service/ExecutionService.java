package interpreter.execution.service;

import interpreter.execution.model.ExecutionContext;

public interface ExecutionService {
    void start();

    void resetCodeAndStack();

    ExecutionContext getExecutionContext();

    void enableProfiling();

    void disableProfiling();
}
