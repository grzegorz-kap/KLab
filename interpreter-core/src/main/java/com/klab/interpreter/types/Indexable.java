package com.klab.interpreter.types;

public interface Indexable extends Sizeable {
    ObjectData get(AddressIterator row, AddressIterator column);

    ObjectData get(AddressIterator cell);
}
