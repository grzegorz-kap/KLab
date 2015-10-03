package interpreter.math;

import interpreter.parsing.model.NumericType;

public abstract class AbstractNumericObject implements NumericObject {

    private NumericType numericType;

    public AbstractNumericObject(NumericType numericType) {
        this.numericType = numericType;
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
