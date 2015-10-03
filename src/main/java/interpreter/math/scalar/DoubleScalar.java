package interpreter.math.scalar;

import interpreter.parsing.model.NumericType;

public class DoubleScalar implements NumericObject {

    private Double value;
    private NumericType numericType;

    public DoubleScalar() {
        setNumericType(NumericType.DOUBLE);
    }

    public DoubleScalar(Double value) {
        this();
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public NumericType getNumericType() {
        return numericType;
    }

    @Override
    public void setNumericType(NumericType numericType) {
        this.numericType = numericType;
    }
}
