package com.klab.interpreter.core.events;

public class InterpreterEvent<T> {
    private T data;
    private Object source;

    public InterpreterEvent(T data, Object source) {
        this.data = data;
        this.source = source;
    }

    public T getData() {
        return data;
    }

    public Object getSource() {
        return source;
    }
}
