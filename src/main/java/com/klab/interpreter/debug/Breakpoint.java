package com.klab.interpreter.debug;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Breakpoint {
    private String sourceId;
    private BreakpointAddress address;
    private boolean released = false;
    private Lock lock;
    private Condition condition;

    public Breakpoint(String sourceId, Integer lineNumber) {
        this.sourceId = sourceId;
        this.address = new BreakpointAddress(lineNumber);
    }

    @Override
    public boolean equals(Object obj) {
        Breakpoint s = obj instanceof Breakpoint ? ((Breakpoint) obj) : null;
        return s != null && address.equals(s.address) && sourceId.equals(s.sourceId);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Breakpoint: '%s', %s", sourceId, address);
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public String getSourceId() {
        return sourceId;
    }

    public BreakpointAddress getAddress() {
        return address;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setAddress(BreakpointAddress address) {
        this.address = address;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
