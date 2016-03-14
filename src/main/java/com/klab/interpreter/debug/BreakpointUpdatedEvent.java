package com.klab.interpreter.debug;

import com.klab.interpreter.core.events.InterpreterEvent;

public class BreakpointUpdatedEvent extends InterpreterEvent<Breakpoint> {
    BreakpointUpdatedEvent(Breakpoint data, Object source) {
        super(data, source);
    }
}
