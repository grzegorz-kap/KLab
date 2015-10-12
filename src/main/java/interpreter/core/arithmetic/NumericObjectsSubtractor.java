package interpreter.core.arithmetic;

import interpreter.types.ObjectData;

public interface NumericObjectsSubtractor extends NumericObjectsOperator {
    ObjectData sub(ObjectData a, ObjectData b);
}
