package interpreter.math.scalar;

import interpreter.math.AbstractNumericObject;
import interpreter.parsing.model.NumericType;

public class DoubleScalar extends AbstractNumericObject {

    private Double value;

    public DoubleScalar() {
        super(NumericType.DOUBLE);
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
}
