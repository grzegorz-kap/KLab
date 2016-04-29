package com.klab.interpreter.core;

import static java.util.Objects.requireNonNull;

public class ExecutionCommand {
    private String body;
    private boolean profiling = false;

    public ExecutionCommand(String body) {
        this.body = body;
    }

    public ExecutionCommand(String body, boolean profiling) {
        this.body = requireNonNull(body);
        this.profiling = profiling;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isProfiling() {
        return profiling;
    }

    public void setProfiling(boolean profiling) {
        this.profiling = profiling;
    }
}