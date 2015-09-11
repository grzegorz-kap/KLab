package interpreter.core.arithmetic.add;

import interpreter.commons.ObjectData;
import interpreter.math.DoubleScalar;

public class DoubleNumberSubtractor implements NumberSubtractor {

    @Override
    public ObjectData sub(ObjectData a, ObjectData b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first - second);
    }
}
