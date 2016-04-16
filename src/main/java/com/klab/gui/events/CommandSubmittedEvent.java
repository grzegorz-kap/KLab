package com.klab.gui.events;

import com.klab.interpreter.core.events.InterpreterEvent;

public class CommandSubmittedEvent extends InterpreterEvent<String> {
    public CommandSubmittedEvent(String data, Object source) {
        super(data, source);
    }
}
