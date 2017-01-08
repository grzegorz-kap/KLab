package com.klab.interpreter.core.events;

import com.klab.interpreter.core.ExecutionCommand;

public class ExecutionStartedEvent extends InterpreterEvent<ExecutionCommand> {
    public ExecutionStartedEvent(ExecutionCommand command, Object source) {
        super(command, source);
    }
}
