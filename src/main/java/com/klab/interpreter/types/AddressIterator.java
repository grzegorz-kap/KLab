package com.klab.interpreter.types;

public interface AddressIterator extends ObjectData {
    boolean hasNext();

    long getNext();

    long length();

    void reset();

    long max();
}
