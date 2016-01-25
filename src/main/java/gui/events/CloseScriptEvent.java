package gui.events;

import interpreter.core.events.InterpreterEvent;

public class CloseScriptEvent extends InterpreterEvent<String> {
    public CloseScriptEvent(String data, Object source) {
        super(data, source);
    }
}
