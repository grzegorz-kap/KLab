package interpreter.core.arithmetic;

import interpreter.commons.ObjectData;

public interface NumericObjectsSubtractor extends NumericObjectsOperator {
    ObjectData sub(ObjectData a, ObjectData b);
}
