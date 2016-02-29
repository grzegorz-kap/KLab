package interpreter.debug;

import interpreter.core.events.InterpreterEvent;

public class BreakPointsUpdatedEvent extends InterpreterEvent<Breakpoint> {
    public BreakPointsUpdatedEvent(Breakpoint data, Object source) {
        super(data, source);
    }
}
