package interpreter.types.converters;

import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.Scalar;
import org.springframework.stereotype.Component;

@Component
public class OjalgoScalarComplexConverter extends AbstractConverter<ComplexScalar> {
    @Override
    protected ComplexScalar convert(Scalar scalar) {
        return new ComplexScalar(scalar.getValue());
    }

    @Override
    public ComplexScalar convert(Matrix<? extends Number> matrix) {
        return new ComplexScalar(evaluteToScalar(matrix));
    }

    @Override
    public NumericType supportFrom() {
        return null;
    }

    @Override
    public NumericType supportTo() {
        return NumericType.COMPLEX_DOUBLE;
    }
}
