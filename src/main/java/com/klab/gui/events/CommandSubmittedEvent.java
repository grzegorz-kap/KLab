package com.klab.gui.events;

import com.klab.interpreter.core.events.InterpreterEvent;

import static java.util.Objects.requireNonNull;

public class CommandSubmittedEvent extends InterpreterEvent<String> {
    private boolean profiling;

    private CommandSubmittedEvent(String data, Object source) {
        super(data, source);
    }

    public static CommandSubmittedEventBuilder create() {
        return new CommandSubmittedEventBuilder();
    }

    public boolean isProfiling() {
        return profiling;
    }

    public void setProfiling(boolean profiling) {
        this.profiling = profiling;
    }

    public static class CommandSubmittedEventBuilder {
        private String data;
        private boolean profiling = false;

        private CommandSubmittedEventBuilder() {
        }

        public CommandSubmittedEventBuilder data(String data) {
            this.data = data;
            return this;
        }

        public CommandSubmittedEventBuilder profiling(boolean profiling) {
            this.profiling = profiling;
            return this;
        }

        public CommandSubmittedEvent build(Object source) {
            CommandSubmittedEvent commandSubmittedEvent = new CommandSubmittedEvent(requireNonNull(data), source);
            commandSubmittedEvent.setProfiling(profiling);
            return commandSubmittedEvent;
        }
    }
}
