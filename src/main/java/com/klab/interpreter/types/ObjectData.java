package com.klab.interpreter.types;

public interface ObjectData extends Copyable {
    default String getName() {
        throw new UnsupportedOperationException();
    }

    default void setName(String name) {
        throw new UnsupportedOperationException();
    }

    default boolean isTrue() {
        throw new UnsupportedOperationException();
    }
}
