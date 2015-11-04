package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;

interface OjalgoBinaryAction<T extends Number> {
    MatrixStore<T> operate(OjalgoMatrix<T> a, OjalgoMatrix<T> b);
}
