package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.Sizeable;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.matrix.store.MatrixStore;

public class MatrixMultiplicator<T extends Number> extends OjalgoOperator<T> {
    public MatrixMultiplicator(OjalgoMatrixCreator<T> creator) {
        super(creator);
    }

    @Override
    protected MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second) {
        if (first.isScalar()) {
            return second.getLazyStore().multiply(first.get(0));
        }
        if (second.isScalar()) {
            return first.getLazyStore().multiply(second.get(0));
        }
        return first.getLazyStore().multiply(second.getLazyStore());
    }

    @Override
    protected void checkSize(Sizeable a, Sizeable b) {
        if (!a.isScalar() && !b.isScalar() && a.getColumns() != b.getRows()) {
            throw new RuntimeException("Sizes does not match!");
        }
    }
}
