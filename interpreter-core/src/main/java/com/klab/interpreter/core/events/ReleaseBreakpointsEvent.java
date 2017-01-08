package com.klab.interpreter.core.events;

public class ReleaseBreakpointsEvent extends InterpreterEvent<Void> {
    public ReleaseBreakpointsEvent(Object source) {
        super(null, source);
    }
}
