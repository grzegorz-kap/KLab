package interpreter.debug;

import interpreter.core.events.InterpreterEvent;

public class BreakpointEvent extends InterpreterEvent<Breakpoint> {
    public enum Operation {
        ADD, REMOVE
    }

    public static BreakpointEvent create(String name, Integer line, Operation operation, Object source) {
        return new BreakpointEvent(new Breakpoint(name, line), source, operation);
    }

    private Operation operation;

    private BreakpointEvent(Breakpoint data, Object source, Operation operation) {
        super(data, source);
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
