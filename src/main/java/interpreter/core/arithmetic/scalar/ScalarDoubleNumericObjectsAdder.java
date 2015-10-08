package interpreter.core.arithmetic.scalar;

import interpreter.commons.ObjectData;
import interpreter.core.arithmetic.NumericObjectsAdder;
import interpreter.math.scalar.DoubleScalar;
import interpreter.parsing.model.NumericType;
import org.springframework.stereotype.Component;

@Component
public class ScalarDoubleNumericObjectsAdder implements NumericObjectsAdder {

    @Override
    public ObjectData add(ObjectData a, ObjectData b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first + second);
    }

    @Override
    public NumericType getSupportedType() {
        return NumericType.DOUBLE;
    }
}
