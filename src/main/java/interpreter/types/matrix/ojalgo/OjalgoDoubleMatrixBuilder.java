package interpreter.types.matrix.ojalgo;


import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;

import java.util.stream.LongStream;

public class OjalgoDoubleMatrixBuilder extends OjalgoAbstractMatrixBuilder<Double> {

    public OjalgoDoubleMatrixBuilder() {
        super(PrimitiveDenseStore.FACTORY);
    }

    @Override
    protected Double convert(Number number) {
        return number.doubleValue();
    }

    @Override
    protected OjalgoMatrix<Double> convert(Matrix<? extends Number> matrix) {
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
    public Matrix<Double> build() {
        return new OjalgoMatrix<>(builder.build());
    }
}
