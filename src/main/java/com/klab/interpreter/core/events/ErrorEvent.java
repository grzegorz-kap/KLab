package com.klab.interpreter.core.events;

import com.klab.interpreter.core.ExecutionError;

public class ErrorEvent extends InterpreterEvent<ExecutionError> {
    public ErrorEvent(ExecutionError data, Object source) {
        super(data, source);
    }
}
