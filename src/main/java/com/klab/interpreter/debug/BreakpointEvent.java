package com.klab.interpreter.debug;

import com.klab.interpreter.core.events.InterpreterEvent;

public class BreakpointEvent extends InterpreterEvent<Breakpoint> {
    private Operation operation;

    private BreakpointEvent(Breakpoint data, Object source, Operation operation) {
        super(data, source);
        this.operation = operation;
    }

    public static BreakpointEvent create(String name, Integer line, Operation operation, Object source) {
        return new BreakpointEvent(new Breakpoint(name, line), source, operation);
    }

    Operation getOperation() {
        return operation;
    }

    public enum Operation {ADD, REMOVE}
}
