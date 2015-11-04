package interpreter.core.arithmetic;

import interpreter.types.NumericObject;

public interface NumericObjectsComparator extends NumericObjectsOperator {

    NumericObject eq(NumericObject a, NumericObject b);

    NumericObject neq(NumericObject a, NumericObject b);

    NumericObject gt(NumericObject a, NumericObject b);

    NumericObject ge(NumericObject a, NumericObject b);

    NumericObject le(NumericObject a, NumericObject b);

    NumericObject lt(NumericObject a, NumericObject b);
}
