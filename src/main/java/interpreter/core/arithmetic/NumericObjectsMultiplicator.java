package interpreter.core.arithmetic;

import interpreter.types.NumericObject;

public interface NumericObjectsMultiplicator extends NumericObjectsOperator {
    NumericObject mult(NumericObject a, NumericObject b);
}
