package com.klab.interpreter.types.matrix.ojalgo;

import org.ojalgo.matrix.store.MatrixStore;

public interface OjalgoMatrixCreator<N extends Number> {
    OjalgoAbstractMatrix<N> create(MatrixStore<N> store);
}
