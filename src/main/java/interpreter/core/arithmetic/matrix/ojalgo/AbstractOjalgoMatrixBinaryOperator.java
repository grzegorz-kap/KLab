package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.commons.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;

public abstract class AbstractOjalgoMatrixBinaryOperator<T extends Number> {

    public ObjectData operate(ObjectData a, ObjectData b) {
        OjalgoMatrix<T> first = (OjalgoMatrix<T>) a;
        OjalgoMatrix<T> second = (OjalgoMatrix<T>) b;
        MatrixStore<T> result = operate(first, second);
        return new OjalgoMatrix<>(result.copy());
    }

    protected abstract MatrixStore<T> operate(OjalgoMatrix<T> first, OjalgoMatrix<T> second);
}
