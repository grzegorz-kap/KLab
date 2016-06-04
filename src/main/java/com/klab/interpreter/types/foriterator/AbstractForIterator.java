package com.klab.interpreter.types.foriterator;

import com.klab.interpreter.types.ObjectData;

abstract class AbstractForIterator implements ForIterator {
    private int address;
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public void setAddress(int index) {
        this.address = index;
    }

    @Override
    public boolean isTrue() {
        return false;
    }

    @Override
    public ObjectData copy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTemp() {
        return false;
    }

    @Override
    public void setTemp(boolean temp) {
        throw new UnsupportedOperationException();
    }
}
