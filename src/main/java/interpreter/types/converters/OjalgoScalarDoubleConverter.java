package interpreter.types.converters;

import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.DoubleScalar;
import interpreter.types.scalar.Scalar;
import org.springframework.stereotype.Component;

@Component
public class OjalgoScalarDoubleConverter extends AbstractConverter {

    @Override
    public NumericType supportFrom() {
        return null;
    }

    @Override
    public NumericType supportTo() {
        return NumericType.DOUBLE;
    }

    @Override
    protected NumericObject convert(Scalar scalar) {
        return new DoubleScalar(scalar.getValue().doubleValue());
    }

    @Override
    protected NumericObject convert(Matrix matrix) {
        return new DoubleScalar(evaluteToScalar(matrix).doubleValue());
    }
}
