package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsComparator;
import interpreter.types.NumericObject;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import org.ojalgo.matrix.store.MatrixStore;

public abstract class AbstractOjalgoMatrixComparator<N extends Number> extends AbstractOjalgoMatrixBinaryOperator<N> implements NumericObjectsComparator {
    @Override
    protected MatrixStore<N> operate(OjalgoAbstractMatrix<N> first, OjalgoAbstractMatrix<N> second) {
        return null;
    }

    public NumericObject eq(NumericObject a, NumericObject b) {
        return operate(a, b, (MatrixStoreAction<N>) this::eq);
    }

    public NumericObject neq(NumericObject a, NumericObject b) {
        return operate(a, b, (MatrixStoreAction<N>) this::neq);
    }

    public NumericObject gt(NumericObject a, NumericObject b) {
        return operate(a, b, (MatrixStoreAction<N>) this::gt);
    }

    public NumericObject ge(NumericObject a, NumericObject b) {
        return operate(a, b, (MatrixStoreAction<N>) this::ge);
    }

    public NumericObject le(NumericObject a, NumericObject b) {
        return operate(a, b, (MatrixStoreAction<N>) this::le);
    }

    public NumericObject lt(NumericObject a, NumericObject b) {
        return operate(a, b, (MatrixStoreAction<N>) this::lt);
    }

    protected abstract MatrixStore<N> eq(MatrixStore<N> first, MatrixStore<N> second);

    protected abstract MatrixStore<N> neq(MatrixStore<N> first, MatrixStore<N> second);

    protected abstract MatrixStore<N> gt(MatrixStore<N> first, MatrixStore<N> second);

    protected abstract MatrixStore<N> ge(MatrixStore<N> first, MatrixStore<N> second);

    protected abstract MatrixStore<N> le(MatrixStore<N> first, MatrixStore<N> second);

    protected abstract MatrixStore<N> lt(MatrixStore<N> first, MatrixStore<N> second);
}
