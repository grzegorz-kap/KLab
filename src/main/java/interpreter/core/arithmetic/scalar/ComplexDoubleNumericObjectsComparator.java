package interpreter.core.arithmetic.scalar;

import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.DoubleScalar;
import org.springframework.stereotype.Component;

@Component
public class ComplexDoubleNumericObjectsComparator extends AbstractComparator {

    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_DOUBLE;
    }

    @Override
    protected ObjectData process(ObjectData a, ObjectData b, int expected) {
        ComplexScalar first = (ComplexScalar) a;
        ComplexScalar second = (ComplexScalar) b;
        return new DoubleScalar(mapToDouble(first.getComplex().compareTo(second.getComplex()) == expected));
    }

    @Override
    protected ObjectData processNot(ObjectData a, ObjectData b, int expected) {
        ComplexScalar first = (ComplexScalar) a;
        ComplexScalar second = (ComplexScalar) b;
        return new DoubleScalar(mapToDouble(first.getComplex().compareTo(second.getComplex()) != expected));
    }

}
