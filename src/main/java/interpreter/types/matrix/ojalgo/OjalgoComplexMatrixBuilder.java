package interpreter.types.matrix.ojalgo;

import interpreter.types.converters.OjalgoMatrixComplexConverter;
import interpreter.types.matrix.Matrix;
import org.ojalgo.matrix.store.ComplexDenseStore;
import org.ojalgo.scalar.ComplexNumber;

public class OjalgoComplexMatrixBuilder extends OjalgoAbstractMatrixBuilder<ComplexNumber> {
    private OjalgoMatrixComplexConverter ojalgoMatrixComplexConverter = new OjalgoMatrixComplexConverter();

    public OjalgoComplexMatrixBuilder() {
        super(ComplexDenseStore.FACTORY);
    }

    @Override
    protected ComplexNumber convert(Number number) {
        return ojalgoMatrixComplexConverter.convert(number);
    }

    @Override
    protected OjalgoMatrix<ComplexNumber> convert(Matrix<? extends Number> matrix) {
        return ojalgoMatrixComplexConverter.convert(matrix);
    }

    @Override
    public OjalgoMatrix<ComplexNumber> build() {
        return new OjalgoComplexMatrix(builder.build());
    }
}
