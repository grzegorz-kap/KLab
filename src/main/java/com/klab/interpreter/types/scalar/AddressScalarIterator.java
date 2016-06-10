package com.klab.interpreter.types.scalar;

import com.klab.interpreter.types.AddressIterator;
import com.klab.interpreter.types.ObjectData;

class AddressScalarIterator implements AddressIterator {
    private boolean nextFlag = true;
    private int value;

    AddressScalarIterator(int value) {
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        return nextFlag;
    }

    @Override
    public int getNext() {
        nextFlag = false;
        return value;
    }

    @Override
    public void setTemp(boolean temp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isTemp() {
        return false;
    }

    @Override
    public ObjectData copy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int max() {
        return value;
    }

    @Override
    public long length() {
        return 1;
    }

    @Override
    public void reset() {
        nextFlag = true;
    }
}
