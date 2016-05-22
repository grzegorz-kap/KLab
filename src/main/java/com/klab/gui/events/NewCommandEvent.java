package com.klab.gui.events;

import com.klab.interpreter.core.events.InterpreterEvent;

public class NewCommandEvent extends InterpreterEvent<String> {
    public NewCommandEvent(String data, Object source) {
        super(data, source);
    }
}
