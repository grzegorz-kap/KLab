package interpreter.core.arithmetic.scalar;

import interpreter.commons.ObjectData;
import interpreter.core.arithmetic.NumberMultiplicator;
import interpreter.math.DoubleScalar;

public class ScalarDoubleNumberMultiplicator implements NumberMultiplicator {
    @Override
    public ObjectData mult(ObjectData a, ObjectData b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first * second);
    }
}
