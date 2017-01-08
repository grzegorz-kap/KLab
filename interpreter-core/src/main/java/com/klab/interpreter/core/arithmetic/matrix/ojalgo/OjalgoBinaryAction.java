package com.klab.interpreter.core.arithmetic.matrix.ojalgo;

import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import org.ojalgo.matrix.store.MatrixStore;

public interface OjalgoBinaryAction<T extends Number> {
    MatrixStore<T> operate(OjalgoAbstractMatrix<T> a, OjalgoAbstractMatrix<T> b);
}

