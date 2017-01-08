package com.klab.interpreter.core.events;

public class ClearConsoleEvent extends InterpreterEvent<Void> {
    public ClearConsoleEvent(Object source) {
        super(null, source);
    }
}