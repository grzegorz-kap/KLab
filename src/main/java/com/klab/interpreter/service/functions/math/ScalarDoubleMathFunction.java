package com.klab.interpreter.service.functions.math;

import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.scalar.ComplexScalar;
import com.klab.interpreter.types.scalar.DoubleScalar;
import org.springframework.stereotype.Component;

@Component
public class ScalarDoubleMathFunction implements MathFunctions {
    @Override
    public NumericType supports() {
        return NumericType.DOUBLE;
    }

    @Override
    public NumericObject sqrt(NumericObject value) {
        double val = getValue(value);
        if (val < 0) {
            return new ComplexScalar(0.0D, Math.sqrt(-val));
        } else {
            return new DoubleScalar(Math.sqrt(val));
        }
    }

    @Override
    public NumericObject log10(NumericObject a) {
        return new DoubleScalar(Math.log10(getValue(a)));
    }

    @Override
    public NumericObject log(NumericObject a) {
        return new DoubleScalar(Math.log(getValue(a)));
    }

    @Override
    public NumericObject log(NumericObject a, NumericObject b) {
        return new DoubleScalar(Math.log10(getValue(b)) / Math.log10(getValue(a)));
    }

    @Override
    public NumericObject inv(NumericObject value) throws Exception {
        return new DoubleScalar(1.0D / getValue(value));
    }

    @Override
    public NumericObject sin(NumericObject value) {
        return new DoubleScalar(Math.sin(getValue(value)));
    }

    @Override
    public NumericObject det(NumericObject value) {
        return value;
    }

    @Override
    public NumericObject tan(NumericObject value) {
        return new DoubleScalar(Math.tan(getValue(value)));
    }

    @Override
    public NumericObject cos(NumericObject value) {
        return new DoubleScalar(Math.cos(getValue(value)));
    }

    protected double getValue(NumericObject value) {
        return ((DoubleScalar) value).getValue();
    }
}
