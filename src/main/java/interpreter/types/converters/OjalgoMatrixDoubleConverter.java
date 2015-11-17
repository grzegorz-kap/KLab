package interpreter.types.converters;

import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
import interpreter.types.scalar.Scalar;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.springframework.stereotype.Component;

import java.util.stream.LongStream;

@Component
public class OjalgoMatrixDoubleConverter extends AbstractConverter<OjalgoDoubleMatrix> {
    private PhysicalStore.Factory<Double, ? extends PhysicalStore<Double>> factory = PrimitiveDenseStore.FACTORY;

    @Override
    protected OjalgoDoubleMatrix convert(Scalar scalar) {
        PhysicalStore<Double> array = factory.makeZero(1, 1);
        array.set(0, scalar.getValue());
        return new OjalgoDoubleMatrix(array);
    }

    @Override
    public OjalgoDoubleMatrix convert(Matrix<? extends Number> matrix) {
        if (NumericType.MATRIX_DOUBLE.equals(matrix.getNumericType())) {
            return (OjalgoDoubleMatrix) matrix;
        } else {
            final PhysicalStore<Number> source = ((OjalgoAbstractMatrix<Number>) matrix).getMatrixStore();
            final PhysicalStore<Double> result = factory.makeZero(matrix.getRows(), matrix.getColumns());
            LongStream.range(0, result.count()).parallel().forEach(index -> result.set(index, source.get(index)));
            return new OjalgoDoubleMatrix(result);
        }
    }

    @Override
    public OjalgoDoubleMatrix convert(Number number) {
        PhysicalStore<Double> array = factory.makeZero(1, 1);
        array.set(0, number);
        return new OjalgoDoubleMatrix(array);
    }

    @Override
    public NumericType supportFrom() {
        return null;
    }

    @Override
    public NumericType supportTo() {
        return NumericType.MATRIX_DOUBLE;
    }
}
