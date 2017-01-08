package com.klab.interpreter.core.arithmetic.scalar;

import com.klab.interpreter.types.NumericObject;

public abstract class AbstractComparator<N extends Number> {
    protected abstract NumericObject process(NumericObject a, NumericObject b, int expected);

    protected abstract NumericObject processNot(NumericObject a, NumericObject b, int expected);

    public NumericObject eq(NumericObject a, NumericObject b) {
        return process(a, b, 0);
    }

    public NumericObject neq(NumericObject a, NumericObject b) {
        return processNot(a, b, 0);
    }

    public NumericObject gt(NumericObject a, NumericObject b) {
        return process(a, b, 1);
    }

    public NumericObject ge(NumericObject a, NumericObject b) {
        return processNot(a, b, -1);
    }

    public NumericObject le(NumericObject a, NumericObject b) {
        return processNot(a, b, 1);
    }

    public NumericObject lt(NumericObject a, NumericObject b) {
        return process(a, b, -1);
    }

    protected double mapToDouble(boolean result) {
        return result ? 1.0D : 0.0D;
    }
}