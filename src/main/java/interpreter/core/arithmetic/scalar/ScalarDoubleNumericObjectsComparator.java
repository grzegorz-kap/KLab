package interpreter.core.arithmetic.scalar;

import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.scalar.DoubleScalar;
import org.springframework.stereotype.Component;

@Component
public class ScalarDoubleNumericObjectsComparator extends AbstractComparator {
    protected NumericObject process(NumericObject a, NumericObject b, int expected) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(mapToDouble(first.compareTo(second) == expected));
    }

    protected NumericObject processNot(NumericObject a, NumericObject b, int expected) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(mapToDouble(first.compareTo(second) != expected));
    }

    @Override
    public NumericType getSupportedType() {
        return NumericType.DOUBLE;
    }
}
