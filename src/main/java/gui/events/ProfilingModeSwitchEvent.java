package gui.events;

import interpreter.core.events.InterpreterEvent;

public class ProfilingModeSwitchEvent extends InterpreterEvent<Boolean> {
    public ProfilingModeSwitchEvent(Boolean data, Object source) {
        super(data, source);
    }
}
