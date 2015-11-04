package interpreter.core.arithmetic.scalar;

import interpreter.core.arithmetic.NumericObjectsMultiplicator;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.scalar.DoubleScalar;
import org.springframework.stereotype.Component;

@Component
public class ScalarNumericObjectsMultiplicator implements NumericObjectsMultiplicator {
    @Override
    public NumericObject mult(NumericObject a, NumericObject b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first * second);
    }

    @Override
    public NumericType getSupportedType() {
        return NumericType.DOUBLE;
    }
}
