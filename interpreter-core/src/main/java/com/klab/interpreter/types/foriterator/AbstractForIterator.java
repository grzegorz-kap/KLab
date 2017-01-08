package com.klab.interpreter.types.foriterator;

import com.klab.interpreter.types.ObjectData;

public abstract class AbstractForIterator implements ForIterator {
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
    public boolean isTrue() {
        return false;
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
    }
}
