package interpreter.core.arithmetic.add;

import interpreter.commons.ObjectData;
import interpreter.math.DoubleScalar;

public class DoubleNumberAdder implements NumberAdder {

    @Override
    public ObjectData add(ObjectData a, ObjectData b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first + second);
    }
}
