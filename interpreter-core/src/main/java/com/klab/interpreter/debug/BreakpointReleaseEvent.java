package com.klab.interpreter.debug;

import com.klab.interpreter.core.events.InterpreterEvent;

public class BreakpointReleaseEvent extends InterpreterEvent<Void> {
    public BreakpointReleaseEvent(Object source) {
        super(null, source);
    }
}
