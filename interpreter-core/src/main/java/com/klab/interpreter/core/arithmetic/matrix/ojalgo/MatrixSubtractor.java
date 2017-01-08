package com.klab.interpreter.core.arithmetic.matrix.ojalgo;

import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.matrix.store.MatrixStore;

public class MatrixSubtractor<N extends Number> extends OjalgoOperator<N> {
    public MatrixSubtractor(OjalgoMatrixCreator<N> creator) {
        super(creator);
    }

    @Override
    protected MatrixStore<N> operate(OjalgoAbstractMatrix<N> first, OjalgoAbstractMatrix<N> second) {
        if (second.isScalar()) {
            return first.getLazyStore().subtract(new OjalgoMatrixScalarWrapper<>(second, first));
        }
        if (first.isScalar()) {
            return new OjalgoMatrixScalarWrapper<>(first, second).subtract(second.getLazyStore());
        }
        return first.getLazyStore().subtract(second.getLazyStore());
    }
}