package com.klab.interpreter.debug;

import com.klab.interpreter.execution.model.InstructionPointer;

/**
 * Created by grzk on 02.08.2016.
 */
public class NoPauseStep implements PauseStep {
    @Override
    public boolean shouldStop(InstructionPointer ip) {
        return false;
    }
}
