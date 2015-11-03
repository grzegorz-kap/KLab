package interpreter.core.arithmetic.scalar;

import interpreter.core.arithmetic.NumericObjectsComparator;
import interpreter.types.ObjectData;

public abstract class AbstractComparator implements NumericObjectsComparator {

    protected abstract ObjectData process(ObjectData a, ObjectData b, int expected);

    protected abstract ObjectData processNot(ObjectData a, ObjectData b, int expected);

    @Override
    public ObjectData eq(ObjectData a, ObjectData b) {
        return process(a, b, 0);
    }

    @Override
    public ObjectData neq(ObjectData a, ObjectData b) {
        return processNot(a, b, 0);
    }

    @Override
    public ObjectData gt(ObjectData a, ObjectData b) {
        return process(a, b, 1);
    }

    @Override
    public ObjectData ge(ObjectData a, ObjectData b) {
        return processNot(a, b, -1);
    }

    @Override
    public ObjectData le(ObjectData a, ObjectData b) {
        return processNot(a, b, 1);
    }

    @Override
    public ObjectData lt(ObjectData a, ObjectData b) {
        return process(a, b, -1);
    }

    protected double mapToDouble(boolean result) {
        return result ? 1.0D : 0.0D;
    }

}