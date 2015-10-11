package interpreter.core.arithmetic;

import interpreter.types.ObjectData;

public interface NumericObjectsDivider extends NumericObjectsOperator {
    ObjectData div(ObjectData a, ObjectData b);
}
