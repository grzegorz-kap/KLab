package interpreter.core.arithmetic;

import interpreter.types.NumericObject;

public interface NumericObjectsSubtractor extends NumericObjectsOperator {
    NumericObject sub(NumericObject a, NumericObject b);
}
