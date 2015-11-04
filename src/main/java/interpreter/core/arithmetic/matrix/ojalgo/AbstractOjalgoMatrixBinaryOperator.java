package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.NumericObject;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;

public abstract class AbstractOjalgoMatrixBinaryOperator<T extends Number> {

    @SuppressWarnings("unchecked")
    protected NumericObject operate(NumericObject a, NumericObject b) {
        OjalgoMatrix<T> first = (OjalgoMatrix<T>) a;
        OjalgoMatrix<T> second = (OjalgoMatrix<T>) b;
        return new OjalgoMatrix<>(operate(first, second));
    }

    @SuppressWarnings("unchecked")
    protected NumericObject operate(NumericObject a, NumericObject b, OjalgoBinaryAction<T> action) {
        OjalgoMatrix<T> first = (OjalgoMatrix<T>) a;
        OjalgoMatrix<T> second = (OjalgoMatrix<T>) b;
        return new OjalgoMatrix<>(action.operate(first, second));
    }

    protected abstract MatrixStore<T> operate(OjalgoMatrix<T> first, OjalgoMatrix<T> second);
}
