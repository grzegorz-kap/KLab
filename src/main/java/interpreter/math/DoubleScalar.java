package interpreter.math;

import interpreter.parsing.model.NumberType;

public class DoubleScalar extends NumberScalar {

    private Double value;

    public DoubleScalar() {
        setNumberType(NumberType.DOUBLE);
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
}
