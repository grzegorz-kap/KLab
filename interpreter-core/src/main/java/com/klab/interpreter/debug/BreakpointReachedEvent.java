package com.klab.interpreter.debug;

import com.klab.interpreter.core.events.InterpreterEvent;

public class BreakpointReachedEvent extends InterpreterEvent<Breakpoint> {
    BreakpointReachedEvent(Breakpoint data, Object source) {
        super(data, source);
    }
}
