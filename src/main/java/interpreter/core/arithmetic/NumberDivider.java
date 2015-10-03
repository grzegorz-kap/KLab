package interpreter.core.arithmetic;

import interpreter.commons.ObjectData;

public interface NumberDivider extends NumberOperator {
    ObjectData div(ObjectData a, ObjectData b);
}
