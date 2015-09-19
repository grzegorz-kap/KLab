package interpreter.core.arithmetic.scalar;

import interpreter.commons.ObjectData;
import interpreter.core.arithmetic.NumberDivider;
import interpreter.math.DoubleScalar;

public class ScalarDoubleNumberDivider implements NumberDivider {
    @Override
    public ObjectData div(ObjectData a, ObjectData b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first / second);
    }
}
