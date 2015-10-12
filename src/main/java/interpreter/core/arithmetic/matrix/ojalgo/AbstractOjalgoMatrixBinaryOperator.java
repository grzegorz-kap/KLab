package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;

public abstract class AbstractOjalgoMatrixBinaryOperator<T extends Number> {

    protected ObjectData operate(ObjectData a, ObjectData b) {
        OjalgoMatrix<T> first = (OjalgoMatrix<T>) a;
        OjalgoMatrix<T> second = (OjalgoMatrix<T>) b;
        MatrixStore<T> result = operate(first, second);
        return new OjalgoMatrix<>(result.copy());
    }

    protected ObjectData operate(ObjectData a, ObjectData b, OjalgoBinaryAction action) {
        OjalgoMatrix<T> first = (OjalgoMatrix<T>) a;
        OjalgoMatrix<T> second = (OjalgoMatrix<T>) b;
        MatrixStore result = action.operate(first, second);
        return new OjalgoMatrix<>(result.copy());
    }


    protected abstract MatrixStore<T> operate(OjalgoMatrix<T> first, OjalgoMatrix<T> second);
}
