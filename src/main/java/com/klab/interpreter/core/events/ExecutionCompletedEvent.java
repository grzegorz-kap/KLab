package com.klab.interpreter.core.events;

public class ExecutionCompletedEvent extends InterpreterEvent<Void> {
    public ExecutionCompletedEvent(Object source) {
        super(null, source);
    }
}
