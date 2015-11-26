package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import org.ojalgo.matrix.store.MatrixStore;

interface OjalgoBinaryAction<T extends Number> {
    MatrixStore<T> operate(OjalgoAbstractMatrix<T> a, OjalgoAbstractMatrix<T> b);
}

interface MatrixStoreAction<T extends Number> {
    MatrixStore<T> operate(MatrixStore<T> a, MatrixStore<T> b);
}
