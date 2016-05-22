package com.klab.interpreter.debug;

import com.klab.interpreter.core.events.InterpreterEvent;

public class RunToEvent extends InterpreterEvent<Void> {
    private String script;
    private int line;

    public RunToEvent(String script, int line, Object source) {
        super(null, source);
        this.script = script;
        this.line = line;
    }

    public String getScript() {
        return script;
    }

    public int getLine() {
        return line;
    }
}