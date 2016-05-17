package com.klab.interpreter.debug;

import com.klab.interpreter.core.events.InterpreterEvent;

public class StepIntoEvent extends InterpreterEvent<Breakpoint> {
    public StepIntoEvent(Breakpoint data, Object source) {
        super(data, source);
    }
}
