package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.types.converters.OjalgoMatrixComplexConverter;
import com.klab.interpreter.types.matrix.Matrix;
import org.ojalgo.matrix.store.ComplexDenseStore;
import org.ojalgo.scalar.ComplexNumber;

public class OjalgoComplexMatrixBuilder extends OjalgoAbstractMatrixBuilder<ComplexNumber> {
    private OjalgoMatrixComplexConverter ojalgoMatrixComplexConverter = new OjalgoMatrixComplexConverter();

    public OjalgoComplexMatrixBuilder() {
        super(ComplexDenseStore.FACTORY);
    }

    @Override
    protected ComplexNumber convert(Number number) {
        return ojalgoMatrixComplexConverter.convertComplex(number);
    }

    @Override
    protected OjalgoAbstractMatrix<ComplexNumber> convert(Matrix<? extends Number> matrix) {
        return ojalgoMatrixComplexConverter.convert(matrix);
    }

    @Override
    public OjalgoAbstractMatrix<ComplexNumber> build() {
        return new OjalgoComplexMatrix(builder.build());
    }
}
