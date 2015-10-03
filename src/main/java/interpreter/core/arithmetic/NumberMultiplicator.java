package interpreter.core.arithmetic;

import interpreter.commons.ObjectData;

public interface NumberMultiplicator extends NumberOperator {
    ObjectData mult(ObjectData a, ObjectData b);
}
