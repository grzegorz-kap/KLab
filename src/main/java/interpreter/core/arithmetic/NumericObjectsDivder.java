package interpreter.core.arithmetic;

import interpreter.types.NumericObject;

public interface NumericObjectsDivder extends NumericObjectsOperator {
    NumericObject div(NumericObject a, NumericObject b);
}
