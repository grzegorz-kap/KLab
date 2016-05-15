package com.klab.interpreter.debug;

import com.klab.interpreter.translate.model.Instruction;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BreakpointImpl implements Breakpoint {
    private String sourceId;
    private boolean released = false;
    private Lock lock;
    private Condition condition;
    private Integer lineNumber;
    private Instruction instruction;

    public BreakpointImpl(String sourceId, Integer lineNumber, Instruction instruction) {
        this.sourceId = sourceId;
        this.lineNumber = lineNumber;
        this.instruction = instruction;
    }

    @Override
    public void block() throws InterruptedException {
        lock = new ReentrantLock();
        condition = lock.newCondition();
        lock.lock();
        try {
            while (!released) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void release() {
        try {
            lock.lock();
            released = true;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean equals(Object obj) {
        BreakpointImpl s = obj instanceof BreakpointImpl ? ((BreakpointImpl) obj) : null;
        return s != null && lineNumber.equals(s.lineNumber
        ) && sourceId.equals(s.sourceId);
    }

    @Deprecated
    @Override
    public int hashCode() {
        return getScriptId().hashCode();
    }

    @Override
    public Integer getLine() {
        return lineNumber;
    }

    @Override
    public Integer getColumn() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("Breakpoint: '%s'", sourceId);
    }

    @Override
    public boolean isReleased() {
        return released;
    }

    @Override
    public Instruction getInstruction() {
        return instruction;
    }

    @Override
    public String getScriptId() {
        return sourceId;
    }
}
