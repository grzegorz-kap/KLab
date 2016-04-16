package com.klab.interpreter.service.functions.external;

public class ExternalReturn {
    private String name;
    private int index;

    public ExternalReturn(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
