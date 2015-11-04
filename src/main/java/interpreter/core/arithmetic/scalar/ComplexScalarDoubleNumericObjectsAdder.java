package interpreter.core.arithmetic.scalar;

import interpreter.core.arithmetic.NumericObjectsAdder;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.scalar.ComplexScalar;
import org.springframework.stereotype.Component;

@Component
public class ComplexScalarDoubleNumericObjectsAdder implements NumericObjectsAdder {
    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_DOUBLE;
    }

    @Override
    public NumericObject add(NumericObject a, NumericObject b) {
        ComplexScalar first = (ComplexScalar) a;
        ComplexScalar second = (ComplexScalar) b;
        return new ComplexScalar(first.getComplex().add(second.getComplex()));
    }
}
