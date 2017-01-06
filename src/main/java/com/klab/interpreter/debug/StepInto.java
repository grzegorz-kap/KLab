package com.klab.interpreter.debug;

import com.klab.interpreter.execution.model.InstructionPointer;

public class StepInto extends Stepover {
    public StepInto(int callLevel, int line) {
        super(callLevel, line);
    }

    @Override
    public boolean shouldStop(InstructionPointer ip) {
        return callLevel < ip.callLevel() || super.shouldStop(ip);
    }
}