package com.klab.interpreter.types;

public interface EditSupplier<N extends Number> {
    boolean hasNext();

    N next();
}
