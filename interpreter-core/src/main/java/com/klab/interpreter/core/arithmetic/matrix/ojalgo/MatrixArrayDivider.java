package com.klab.interpreter.core.arithmetic.matrix.ojalgo;

import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.matrix.store.MatrixStore;

public class MatrixArrayDivider<T extends Number> extends OjalgoOperator<T> {
    public MatrixArrayDivider(OjalgoMatrixCreator<T> creator) {
        super(creator);
    }

    @Override
    protected MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second) {
        MatrixStore<T> f = first.isScalar() ? new OjalgoMatrixScalarWrapper<T>(first, second) : first.getLazyStore();
        MatrixStore<T> s = second.isScalar() ? new OjalgoMatrixScalarWrapper<T>(second, first) : second.getLazyStore();
        return f.operateOnMatching(s.factory().function().divide(), s).get();
    }
}
