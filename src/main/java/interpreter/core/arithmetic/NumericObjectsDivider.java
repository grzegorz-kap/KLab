package interpreter.core.arithmetic;

import interpreter.commons.ObjectData;

public interface NumericObjectsDivider extends NumericObjectsOperator {
    ObjectData div(ObjectData a, ObjectData b);
}
