package interpreter.core.arithmetic.scalar;

import interpreter.core.arithmetic.NumericObjectsMultiplicator;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.ComplexScalar;
import org.springframework.stereotype.Component;

@Component
public class ComplexScalarDoubleNumericObjectsMultiplicator implements NumericObjectsMultiplicator {

    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_DOUBLE;
    }

    @Override
    public ObjectData mult(ObjectData a, ObjectData b) {
        ComplexScalar first = (ComplexScalar) a;
        ComplexScalar second = (ComplexScalar) b;
        return new ComplexScalar(first.getComplex().multiply(second.getComplex()));
    }

}
