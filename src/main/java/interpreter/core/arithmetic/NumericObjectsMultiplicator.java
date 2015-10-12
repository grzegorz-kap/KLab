package interpreter.core.arithmetic;

import interpreter.types.ObjectData;

public interface NumericObjectsMultiplicator extends NumericObjectsOperator {
    ObjectData mult(ObjectData a, ObjectData b);
}
