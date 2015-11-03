package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;

public abstract class AbstractOjalgoMatrixBinaryOperator<T extends Number> {

    @SuppressWarnings("unchecked")
    protected ObjectData operate(ObjectData a, ObjectData b) {
        OjalgoMatrix<T> first = (OjalgoMatrix<T>) a;
        OjalgoMatrix<T> second = (OjalgoMatrix<T>) b;
        return new OjalgoMatrix<>(operate(first, second));
    }

    @SuppressWarnings("unchecked")
    protected ObjectData operate(ObjectData a, ObjectData b, OjalgoBinaryAction<T> action) {
        OjalgoMatrix<T> first = (OjalgoMatrix<T>) a;
        OjalgoMatrix<T> second = (OjalgoMatrix<T>) b;
        return new OjalgoMatrix<>(action.operate(first, second));
    }

    protected abstract MatrixStore<T> operate(OjalgoMatrix<T> first, OjalgoMatrix<T> second);
}
