package interpreter.core.arithmetic.scalar;

import interpreter.commons.ObjectData;
import interpreter.math.scalar.DoubleScalar;
import interpreter.parsing.model.NumericType;
import org.springframework.stereotype.Component;

@Component
public class ScalarNumericObjectsMultiplicator implements interpreter.core.arithmetic.NumericObjectsMultiplicator {

    @Override
    public ObjectData mult(ObjectData a, ObjectData b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first * second);
    }

    @Override
    public NumericType getSupportedType() {
        return NumericType.DOUBLE;
    }
}
