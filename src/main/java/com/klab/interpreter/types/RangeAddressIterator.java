package com.klab.interpreter.types;

import java.util.NoSuchElementException;

public class RangeAddressIterator implements AddressIterator, Addressable {
    private long originalStart;
    private long start;
    private long end;
    private long length;

    public RangeAddressIterator(long start, long end) {
        this.start = start;
        this.originalStart = start;
        this.end = end;
        this.length = end - start + 1;
    }

    @Override
    public boolean hasNext() {
        return start <= end;
    }

    @Override
    public long getNext() {
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
    public long max() {
        return end;
    }

    @Override
    public AddressIterator getAddressIterator() {
        return this;
    }
}
