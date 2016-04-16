package com.klab.gui.events;

import com.klab.interpreter.core.events.InterpreterEvent;

public class OpenScriptEvent extends InterpreterEvent<String> {
    public OpenScriptEvent(String data, Object source) {
        super(data, source);
    }
}
