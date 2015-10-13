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

    @Override
    public ObjectData neq(ObjectData a, ObjectData b) {
        DoubleScalar first = (DoubleScalar) a;
        DoubleScalar second = (DoubleScalar) b;
        return new DoubleScalar(mapToDouble(!first.getValue().equals(second.getValue())));
    }

    @Override
    public ObjectData gt(ObjectData a, ObjectData b) {
        DoubleScalar first = (DoubleScalar) a;
        DoubleScalar second = (DoubleScalar) b;
        return new DoubleScalar(first.getValue().compareTo(second.getValue()) == 1 ? 1.0D : 0.0D);
    }

    @Override
    public ObjectData ge(ObjectData a, ObjectData b) {
        Double first = ((DoubleScalar) a).getValue();
        Double second = ((DoubleScalar) b).getValue();
        return new DoubleScalar(first.compareTo(second) >= 0 ? 1.0D : 0.0D);
    }

    private double mapToDouble(boolean result) {
        return result ? 1.0D : 0.0D;
    }

    @Override
    public NumericType getSupportedType() {
        return NumericType.DOUBLE;
    }
}
