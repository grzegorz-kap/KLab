package com.klab.interpreter.types;

public interface AddressIterator extends ObjectData {
    boolean hasNext();

    int getNext();

    long length();

    void reset();

    int max();
}
