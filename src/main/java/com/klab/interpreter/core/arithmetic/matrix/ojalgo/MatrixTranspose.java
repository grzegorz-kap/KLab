package com.klab.interpreter.core.arithmetic.matrix.ojalgo;

import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;

public class MatrixTranspose<N extends Number> {
    private OjalgoMatrixCreator<N> creator;

    public MatrixTranspose(OjalgoMatrixCreator<N> creator) {
        this.creator = creator;
    }

    @SuppressWarnings("unchecked")
    protected NumericObject operate(NumericObject a) {
        return creator.create(((OjalgoAbstractMatrix<N>) a).getLazyStore().transpose());
    }
}
