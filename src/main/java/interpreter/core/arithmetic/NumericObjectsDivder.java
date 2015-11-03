package interpreter.core.arithmetic;

import interpreter.types.ObjectData;

public interface NumericObjectsDivder extends NumericObjectsOperator {
    ObjectData div(ObjectData a, ObjectData b);
}
