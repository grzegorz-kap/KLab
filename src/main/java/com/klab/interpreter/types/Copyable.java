package com.klab.interpreter.types;

public interface Copyable {
    default ObjectData copyObjectData() {
        throw new UnsupportedOperationException();
    }
}