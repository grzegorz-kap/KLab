package interpreter.core.arithmetic;

import interpreter.types.NumericObject;

public interface NumericObjectsAdder extends NumericObjectsOperator {
    NumericObject add(final NumericObject a, final NumericObject b);
}
