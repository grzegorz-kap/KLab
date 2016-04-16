package com.klab.gui.events;

import com.klab.interpreter.core.events.InterpreterEvent;

public class ProfilingModeSwitchEvent extends InterpreterEvent<Boolean> {
    public ProfilingModeSwitchEvent(Boolean data, Object source) {
        super(data, source);
    }
}
