package interpreter.core.arithmetic.scalar;

import interpreter.core.arithmetic.NumericObjectsComparator;
import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.DoubleScalar;
import org.springframework.stereotype.Component;

@Component
public class ScalarDoubleNumericObjectsComparator implements NumericObjectsComparator {

    @Override
    public ObjectData eq(ObjectData a, ObjectData b) {
        DoubleScalar first = (DoubleScalar) a;
        DoubleScalar second = (DoubleScalar) b;
        return new DoubleScalar(mapToDouble(first.getValue().equals(second.getValue())));
    }

    private double mapToDouble(boolean result) {
        return result ? 1.0D : 0.0D;
    }

    @Override
    public NumericType getSupportedType() {
        return NumericType.DOUBLE;
    }
}
