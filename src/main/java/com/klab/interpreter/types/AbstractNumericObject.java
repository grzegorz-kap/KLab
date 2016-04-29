package com.klab.interpreter.types;

public abstract class AbstractNumericObject extends AbstractObjectData implements NumericObject {

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
