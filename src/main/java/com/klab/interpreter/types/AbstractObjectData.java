package com.klab.interpreter.types;

public abstract class AbstractObjectData implements ObjectData {
    private String name;
    private int address;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public int getAddress() {
        return address;
    }
}
