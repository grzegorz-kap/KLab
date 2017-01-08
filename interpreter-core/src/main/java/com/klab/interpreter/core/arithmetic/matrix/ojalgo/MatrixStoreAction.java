package com.klab.interpreter.core.arithmetic.matrix.ojalgo;

import org.ojalgo.matrix.store.MatrixStore;

public interface MatrixStoreAction<T extends Number> {
    MatrixStore<T> operate(MatrixStore<T> a, MatrixStore<T> b);
}
