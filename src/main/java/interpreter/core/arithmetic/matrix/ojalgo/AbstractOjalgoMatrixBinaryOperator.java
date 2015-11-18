package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.NumericObject;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import org.ojalgo.matrix.store.MatrixStore;

public abstract class AbstractOjalgoMatrixBinaryOperator<T extends Number> {
    protected abstract OjalgoAbstractMatrix<T> create(MatrixStore<T> matrixStore);

    @SuppressWarnings("unchecked")
    protected NumericObject operate(NumericObject a, NumericObject b) {
        OjalgoAbstractMatrix<T> first = (OjalgoAbstractMatrix<T>) a;
        OjalgoAbstractMatrix<T> second = (OjalgoAbstractMatrix<T>) b;
        return create(operate(first, second));
    }

    @SuppressWarnings("unchecked")
    protected NumericObject operate(NumericObject a, NumericObject b, OjalgoBinaryAction<T> action) {
        OjalgoAbstractMatrix<T> first = (OjalgoAbstractMatrix<T>) a;
        OjalgoAbstractMatrix<T> second = (OjalgoAbstractMatrix<T>) b;
        return create(action.operate(first, second));
    }

    protected abstract MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second);
}
