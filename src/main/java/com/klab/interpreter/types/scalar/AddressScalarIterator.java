package com.klab.interpreter.types.scalar;

import com.klab.interpreter.types.AddressIterator;

public class AddressScalarIterator implements AddressIterator {
    private boolean nextFlag = true;
    private long value;

    public AddressScalarIterator(int value) {
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        return nextFlag;
    }

    @Override
    public long getNext() {
        nextFlag = false;
        return value;
    }

    @Override
    public long max() {
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

    @Override
    public int getAddress() {
        return -1;
    }

    @Override
    public void setAddress(int index) {
        throw new UnsupportedOperationException();
    }
}
