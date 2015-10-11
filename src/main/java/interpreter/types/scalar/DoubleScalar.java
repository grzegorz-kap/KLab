package interpreter.types.scalar;

import interpreter.parsing.model.NumericType;
import interpreter.types.AbstractNumericObject;
import interpreter.types.ObjectData;

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

    @Override
    public ObjectData copyObjectData() {
        return new DoubleScalar(value);
    }
}
