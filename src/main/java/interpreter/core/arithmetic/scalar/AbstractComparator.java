package interpreter.core.arithmetic.scalar;

import interpreter.core.arithmetic.NumericObjectsComparator;
import interpreter.types.NumericObject;

public abstract class AbstractComparator implements NumericObjectsComparator {
    protected abstract NumericObject process(NumericObject a, NumericObject b, int expected);

    protected abstract NumericObject processNot(NumericObject a, NumericObject b, int expected);

    @Override
    public NumericObject eq(NumericObject a, NumericObject b) {
        return process(a, b, 0);
    }

    @Override
    public NumericObject neq(NumericObject a, NumericObject b) {
        return processNot(a, b, 0);
    }

    @Override
    public NumericObject gt(NumericObject a, NumericObject b) {
        return process(a, b, 1);
    }

    @Override
    public NumericObject ge(NumericObject a, NumericObject b) {
        return processNot(a, b, -1);
    }

    @Override
    public NumericObject le(NumericObject a, NumericObject b) {
        return processNot(a, b, 1);
    }

    @Override
    public NumericObject lt(NumericObject a, NumericObject b) {
        return process(a, b, -1);
    }

    protected double mapToDouble(boolean result) {
        return result ? 1.0D : 0.0D;
    }
}