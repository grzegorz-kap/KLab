package interpreter.core.arithmetic;

import interpreter.commons.ObjectData;

public interface NumberSubtractor extends NumberOperator {
    ObjectData sub(ObjectData a, ObjectData b);
}
