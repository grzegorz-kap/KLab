package com.klab.interpreter.debug;

import com.klab.interpreter.core.events.InterpreterEvent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BreakpointReachedEvent extends InterpreterEvent<Breakpoint> {
    BreakpointReachedEvent(Breakpoint data, Object source) {
        super(data, source);
    }

    public Lock getLock() {
        return getData().getLock();
    }

    public Condition getReleasedCondition() {
        return getData().getCondition();
    }
}
