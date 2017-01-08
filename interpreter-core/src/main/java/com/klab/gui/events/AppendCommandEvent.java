package com.klab.gui.events;

import com.klab.interpreter.core.events.InterpreterEvent;

public class AppendCommandEvent extends InterpreterEvent<String> {
    public AppendCommandEvent(String data, Object source) {
        super(data, source);
    }
}
