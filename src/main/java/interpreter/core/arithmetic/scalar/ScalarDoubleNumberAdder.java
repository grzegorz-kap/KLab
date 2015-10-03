package interpreter.core.arithmetic.scalar;

import interpreter.commons.ObjectData;
import interpreter.core.arithmetic.NumberAdder;
import interpreter.math.scalar.DoubleScalar;

public class ScalarDoubleNumberAdder implements NumberAdder {

    @Override
    public ObjectData add(ObjectData a, ObjectData b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first + second);
    }
}
