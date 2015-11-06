package interpreter.types.converters;

import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import interpreter.types.scalar.Scalar;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.springframework.stereotype.Component;

import java.util.stream.LongStream;

@Component
public class OjalgoMatrixDoubleConverter extends AbstractConverter<OjalgoMatrix<Double>> {
    private PhysicalStore.Factory<Double, ? extends PhysicalStore<Double>> factory = PrimitiveDenseStore.FACTORY;

    @Override
    protected OjalgoMatrix<Double> convert(Scalar scalar) {
        return convert(scalar.getValue());
    }

    @Override
    public OjalgoMatrix<Double> convert(Matrix<? extends Number> matrix) {
        if (NumericType.MATRIX_DOUBLE.equals(matrix.getNumericType())) {
            return (OjalgoMatrix<Double>) matrix;
        } else {
            final PhysicalStore<Number> source = ((OjalgoMatrix<Number>) matrix).getMatrixStore();
            final PhysicalStore<Double> result = factory.makeZero(matrix.getRows(), matrix.getColumns());
            LongStream.range(0, result.count()).parallel().forEach(index -> result.set(index, source.get(index)));
            return new OjalgoMatrix<>(result);
        }
    }

    @Override
    public OjalgoMatrix<Double> convert(Number number) {
        PhysicalStore<Double> array = factory.makeZero(1, 1);
        array.set(0, number);
        return new OjalgoMatrix<>(array);
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
