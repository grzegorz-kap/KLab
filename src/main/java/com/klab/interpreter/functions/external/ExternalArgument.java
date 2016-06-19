package com.klab.interpreter.functions.external;

public class ExternalArgument {
    private int index;
    private String name;

    public ExternalArgument(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
