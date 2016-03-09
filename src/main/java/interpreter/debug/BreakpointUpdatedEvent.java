package interpreter.debug;

import interpreter.core.events.InterpreterEvent;

public class BreakpointUpdatedEvent extends InterpreterEvent<Breakpoint> {
    BreakpointUpdatedEvent(Breakpoint data, Object source) {
        super(data, source);
    }
}
