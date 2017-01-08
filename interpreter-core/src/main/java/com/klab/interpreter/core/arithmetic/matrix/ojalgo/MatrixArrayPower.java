package com.klab.interpreter.core.arithmetic.matrix.ojalgo;

import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.matrix.store.MatrixStore;

public class MatrixArrayPower<N extends Number> extends OjalgoOperator<N> {
    public MatrixArrayPower(OjalgoMatrixCreator<N> creator) {
        super(creator);
    }

    @Override
    protected MatrixStore<N> operate(OjalgoAbstractMatrix<N> first, OjalgoAbstractMatrix<N> second) {
        MatrixStore<N> f = first.isScalar() ? new OjalgoMatrixScalarWrapper<>(first, second) : first.getLazyStore();
        MatrixStore<N> s = second.isScalar() ? new OjalgoMatrixScalarWrapper<>(second, first) : second.getLazyStore();
        return f.operateOnMatching(f.factory().function().pow(), s).get();
    }
}
