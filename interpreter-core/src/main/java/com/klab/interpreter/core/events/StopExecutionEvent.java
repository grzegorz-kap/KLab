package com.klab.interpreter.core.events;

public class StopExecutionEvent extends InterpreterEvent<Void> {
    public StopExecutionEvent(Object source) {
        super(null, source);
    }
}
