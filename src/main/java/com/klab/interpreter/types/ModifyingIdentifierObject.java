package com.klab.interpreter.types;

public class ModifyingIdentifierObject implements IdentifierObject {
    private int address;
    private String id;

    public ModifyingIdentifierObject(int address, String id) {
        this.address = address;
        this.id = id;
    }

    @Override
    public boolean isCanBeScript() {
        return false;
    }

    @Override
    public void setCanBeScript(boolean canBeScript) {
    }

    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public ObjectData copy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTemp() {
        return true;
    }

    @Override
    public void setTemp(boolean temp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return id;
    }

    @Override
    public void setName(String name) {
        this.id = name;
    }

    @Override
    public boolean isTrue() {
        return false;
    }
}
