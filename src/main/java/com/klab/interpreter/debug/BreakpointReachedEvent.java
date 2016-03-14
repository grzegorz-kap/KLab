package com.klab.interpreter.debug;

import com.klab.interpreter.core.events.InterpreterEvent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BreakpointReachedEvent extends InterpreterEvent<Breakpoint> {
    private Lock lock;
    private Condition released;

    public BreakpointReachedEvent(Breakpoint data, Object source, Lock lock, Condition released) {
        super(data, source);
        this.lock = lock;
        this.released = released;
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getReleased() {
        return released;
    }
}
