package com.klab.interpreter.debug;

public class ExecutionStep {
    private int line;
    private int callStackLevel;
    private boolean stepInto = false;

    public ExecutionStep(int line, int callStackLevel, boolean stepInto) {
        this.line = line;
        this.callStackLevel = callStackLevel;
        this.stepInto = stepInto;
    }

    public int getLine() {
        return line;
    }

    public int getCallLevel() {
        return callStackLevel;
    }

    public boolean isStepInto() {
        return stepInto;
    }
}
