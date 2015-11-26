package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.NumericObject;
import interpreter.types.Sizeable;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import org.ojalgo.matrix.store.MatrixStore;

public abstract class AbstractOjalgoMatrixBinaryOperator<T extends Number> {
    protected abstract OjalgoAbstractMatrix<T> create(MatrixStore<T> matrixStore);

    @SuppressWarnings("unchecked")
    protected NumericObject operate(NumericObject a, NumericObject b) {
        OjalgoAbstractMatrix<T> first = (OjalgoAbstractMatrix<T>) a;
        OjalgoAbstractMatrix<T> second = (OjalgoAbstractMatrix<T>) b;
        checkSize(first, second);
        return create(operate(first, second));
    }

    @SuppressWarnings("unchecked")
    protected NumericObject operate(NumericObject a, NumericObject b, OjalgoBinaryAction<T> action) {
        OjalgoAbstractMatrix<T> first = (OjalgoAbstractMatrix<T>) a;
        OjalgoAbstractMatrix<T> second = (OjalgoAbstractMatrix<T>) b;
        checkSize(first, second);
        return create(action.operate(first, second));
    }

    protected abstract MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second);

    protected void checkSize(Sizeable a, Sizeable b) {
        if (!a.isScalar() && !b.isScalar() && (a.getRows() != b.getRows() || a.getColumns() != b.getColumns())) {
            throw new RuntimeException("Sizes does not match!");
        }
    }
}
