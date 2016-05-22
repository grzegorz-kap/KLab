package com.klab.interpreter.types;

public interface Sizeable {
    long getRows();

    long getColumns();

    long getCells();

    default boolean isScalar() {
        return getRows() == 1 && getColumns() == 1;
    }

    default String formatToString() {
        return String.format("<%d x %d>", getRows(), getColumns());
    }
}
