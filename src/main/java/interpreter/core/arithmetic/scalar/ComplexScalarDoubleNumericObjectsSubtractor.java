package interpreter.core.arithmetic.scalar;

import interpreter.core.arithmetic.NumericObjectsSubtractor;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.scalar.ComplexScalar;
import org.springframework.stereotype.Component;

@Component
public class ComplexScalarDoubleNumericObjectsSubtractor implements NumericObjectsSubtractor {
    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_DOUBLE;
    }

    @Override
    public NumericObject sub(NumericObject a, NumericObject b) {
        ComplexScalar first = (ComplexScalar) a;
        ComplexScalar second = (ComplexScalar) b;
        return new ComplexScalar(first.getComplex().subtract(second.getComplex()));
    }
}
