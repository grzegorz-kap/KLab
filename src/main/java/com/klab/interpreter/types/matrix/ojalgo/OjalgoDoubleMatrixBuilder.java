package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.types.converters.OjalgoMatrixDoubleConverter;
import com.klab.interpreter.types.matrix.Matrix;
import org.ojalgo.matrix.store.PrimitiveDenseStore;

class OjalgoDoubleMatrixBuilder extends OjalgoAbstractMatrixBuilder<Double> {
    private OjalgoMatrixDoubleConverter ojalgoMatrixDoubleConverter = new OjalgoMatrixDoubleConverter();

    OjalgoDoubleMatrixBuilder() {
        super(PrimitiveDenseStore.FACTORY);
    }

    @Override
    protected Double convert(Number number) {
        return number.doubleValue();
    }

    @Override
    protected OjalgoDoubleMatrix convert(Matrix<? extends Number> matrix) {
        return ojalgoMatrixDoubleConverter.convert(matrix);
    }

    @Override
    public Matrix<Double> build() {
        if (builder != null) {
            return new OjalgoDoubleMatrix(builder.get());
        } else {
            return new OjalgoDoubleMatrix(factory.makeZero(0, 0));
        }
    }
}
