package com.klab.interpreter.core;

import com.klab.interpreter.execution.model.Code;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public interface Interpreter {
    Lock MAIN_LOCK = new ReentrantLock();

    void startAsync(ExecutionCommand cmd);

    void startSync(ExecutionCommand cmd);

    List<Code> callStack();

    boolean executionInProgress();
}
