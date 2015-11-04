package interpreter.types.converters;

import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.Scalar;
import org.springframework.stereotype.Component;

@Component
public class OjalgoScalarComplexConverter extends AbstractConverter {
    @Override
    protected NumericObject convert(Scalar scalar) {
        return new ComplexScalar(scalar.getValue());
    }

    @Override
    protected NumericObject convert(Matrix matrix) {
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
