package interpreter.types.matrix.ojalgo;

import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import org.ojalgo.matrix.store.ComplexDenseStore;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.scalar.ComplexNumber;

public class OjalgoComplexMatrixBuilder extends OjalgoAbstractMatrixBuilder<ComplexNumber> {

    public OjalgoComplexMatrixBuilder() {
        super(ComplexDenseStore.FACTORY);
    }

    @Override
    protected ComplexNumber convert(Number number) {
        return ComplexNumber.FACTORY.cast(number);
    }

    @Override
    protected OjalgoMatrix<ComplexNumber> convert(Matrix<? extends Number> matrix) {
        if (NumericType.COMPLEX_MATRIX.equals(matrix.getNumericType())) {
            return ((OjalgoComplexMatrix) matrix);
        }
        PhysicalStore<Number> source = ((OjalgoMatrix) matrix).getMatrixStore();
        PhysicalStore<ComplexNumber> destination = factory.makeZero(source.countRows(), source.countColumns());
        final long length = destination.count();
        for (long index = 0; index < length; index++) {
            destination.set(index, convert(destination.get(index)));
        }
        return new OjalgoComplexMatrix(destination);
    }

    @Override
    public OjalgoMatrix<ComplexNumber> build() {
        return new OjalgoComplexMatrix(builder.build());
    }
}
