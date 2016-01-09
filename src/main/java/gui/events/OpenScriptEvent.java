package gui.events;

import interpreter.core.events.InterpreterEvent;

public class OpenScriptEvent extends InterpreterEvent<String> {
    public OpenScriptEvent(String data, Object source) {
        super(data, source);
    }
}
