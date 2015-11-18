package interpreter.types.converters;

import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import interpreter.types.scalar.Scalar;
import org.ojalgo.matrix.store.ComplexDenseStore;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.stereotype.Component;

@Component
public class OjalgoMatrixComplexConverter extends AbstractConverter<OjalgoComplexMatrix> {

    @Override
    protected OjalgoComplexMatrix convert(Scalar scalar) {
        return convert(scalar.getValue());
    }

    @Override
    public OjalgoComplexMatrix convert(Matrix<? extends Number> matrix) {
        if (NumericType.COMPLEX_MATRIX.equals(matrix.getNumericType())) {
            return ((OjalgoComplexMatrix) matrix);
        }
        PhysicalStore<Number> source = ((OjalgoAbstractMatrix<Number>) matrix).getMatrixStore();
        PhysicalStore<ComplexNumber> destination = ComplexDenseStore.FACTORY.makeZero(source.countRows(), source.countColumns());
        final long length = destination.count();
        for (long index = 0; index < length; index++) {
            destination.set(index, convertComplex(destination.get(index)));
        }
        return new OjalgoComplexMatrix(destination);
    }

    public ComplexNumber convertComplex(Number number) {
        return ComplexNumber.FACTORY.cast(number);
    }

    @Override
    public OjalgoComplexMatrix convert(Number number) {
        PhysicalStore<ComplexNumber> physicalStore = ComplexDenseStore.FACTORY.makeZero(1, 1);
        physicalStore.set(0, number);
        return new OjalgoComplexMatrix(physicalStore);
    }

    @Override
    public NumericType supportFrom() {
        return null;
    }

    @Override
    public NumericType supportTo() {
        return NumericType.COMPLEX_MATRIX;
    }
}
