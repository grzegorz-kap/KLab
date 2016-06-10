package com.klab.interpreter.types;

import java.util.NoSuchElementException;

public class RangeAddressIterator implements AddressIterator, Addressable {
    private int originalStart;
    private int start;
    private int end;
    private int length;

    public RangeAddressIterator(int start, int end) {
        this.start = start;
        this.originalStart = start;
        this.end = end;
        this.length = end - start + 1;
    }

    @Override
    public boolean isTemp() {
        return false;
    }

    @Override
    public ObjectData copy() {
        return new RangeAddressIterator(start, end);
    }

    @Override
    public void setTemp(boolean temp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
        return start <= end;
    }

    @Override
    public int getNext() {
        if (hasNext()) {
            return start++;
        } else {
            throw new NoSuchElementException(); // TODO
        }
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public void reset() {
        start = originalStart;
    }

    @Override
    public int max() {
        return end;
    }

    @Override
    public AddressIterator getAddressIterator() {
        return this;
    }
}
