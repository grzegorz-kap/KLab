package interpreter.types.scalar;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;

import java.util.Objects;

public class DoubleScalar extends AbstractScalar {

    private Double value;

    public DoubleScalar() {
        super(NumericType.DOUBLE);
    }

    public DoubleScalar(Double value) {
        this();
        this.value = value;
    }

    public DoubleScalar(Number number) {
        this();
        this.value = number.doubleValue();
    }

    @Override
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public int getIntOrThrow() {
        if (isMathematicalInteger()) {
            return value.intValue();
        } else {
            throw new InterpreterCastException(InterpreterCastException.EXPECTED_INTEGER_VALUE);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public ObjectData copyObjectData() {
        return new DoubleScalar(value);
    }

    @Override
    public boolean isTrue() {
        return Objects.nonNull(value) && !value.equals(0.0D);
    }

    public boolean isMathematicalInteger() {
        return Math.rint(value) == value;
    }
}
