package com.klab.interpreter.debug;

import com.klab.interpreter.core.events.InterpreterEvent;

public class StepOverEvent extends InterpreterEvent<Breakpoint> {
    public StepOverEvent(Breakpoint data, Object source) {
        super(data, source);
    }
}
