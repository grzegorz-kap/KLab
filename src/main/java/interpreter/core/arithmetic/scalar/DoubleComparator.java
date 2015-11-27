package interpreter.core.arithmetic.scalar;

import interpreter.types.NumericObject;
import interpreter.types.scalar.DoubleScalar;

public class DoubleComparator extends AbstractComparator<Double> {
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
}
