package com.klab.interpreter.core.events;

import com.klab.interpreter.core.ExecutionCommand;

public class ExecutionCompletedEvent extends InterpreterEvent<ExecutionCommand> {
    public ExecutionCompletedEvent(ExecutionCommand command, Object source) {
        super(command, source);
    }
}
