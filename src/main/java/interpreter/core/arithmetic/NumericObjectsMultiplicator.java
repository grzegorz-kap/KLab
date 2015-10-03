package interpreter.core.arithmetic;

import interpreter.commons.ObjectData;

public interface NumericObjectsMultiplicator extends NumericObjectsOperator {
    ObjectData mult(ObjectData a, ObjectData b);
}
