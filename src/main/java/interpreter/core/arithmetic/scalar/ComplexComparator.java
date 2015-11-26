package interpreter.core.arithmetic.scalar;

import interpreter.types.NumericObject;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.DoubleScalar;
import org.ojalgo.scalar.ComplexNumber;

public class ComplexComparator extends AbstractComparator<ComplexNumber> {
    @Override
    protected NumericObject process(NumericObject a, NumericObject b, int expected) {
        ComplexScalar first = (ComplexScalar) a;
        ComplexScalar second = (ComplexScalar) b;
        return new DoubleScalar(mapToDouble(first.getComplex().compareTo(second.getComplex()) == expected));
    }

    @Override
    protected NumericObject processNot(NumericObject a, NumericObject b, int expected) {
        ComplexScalar first = (ComplexScalar) a;
        ComplexScalar second = (ComplexScalar) b;
        return new DoubleScalar(mapToDouble(first.getComplex().compareTo(second.getComplex()) != expected));
    }
}
