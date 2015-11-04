package interpreter.types.matrix.ojalgo;

import interpreter.types.converters.OjalgoMatrixDoubleConverter;
import interpreter.types.matrix.Matrix;
import org.ojalgo.matrix.store.PrimitiveDenseStore;

public class OjalgoDoubleMatrixBuilder extends OjalgoAbstractMatrixBuilder<Double> {
    private OjalgoMatrixDoubleConverter ojalgoMatrixDoubleConverter = new OjalgoMatrixDoubleConverter();

    public OjalgoDoubleMatrixBuilder() {
        super(PrimitiveDenseStore.FACTORY);
    }

    @Override
    protected Double convert(Number number) {
        return number.doubleValue();
    }

    @Override
    protected OjalgoMatrix<Double> convert(Matrix<? extends Number> matrix) {
        return ojalgoMatrixDoubleConverter.convert(matrix);
    }

    @Override
    public Matrix<Double> build() {
        return new OjalgoMatrix<>(builder.build());
    }
}
