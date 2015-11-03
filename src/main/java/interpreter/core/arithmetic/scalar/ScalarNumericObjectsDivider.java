package interpreter.core.arithmetic.scalar;

import interpreter.core.arithmetic.NumericObjectsDivder;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.DoubleScalar;
import org.springframework.stereotype.Component;

@Component
public class ScalarNumericObjectsDivider implements NumericObjectsDivder {

    @Override
    public ObjectData div(ObjectData a, ObjectData b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first / second);
    }

    @Override
    public NumericType getSupportedType() {
        return NumericType.DOUBLE;
    }
}
