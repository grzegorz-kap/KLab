package interpreter.core.arithmetic;

import interpreter.types.NumericObject;
import interpreter.types.NumericType;

public interface NumericObjectsOperator {
    NumericType getSupportedType();
    
    NumericObject transpose(NumericObject a);

    NumericObject add(final NumericObject a, final NumericObject b);

    NumericObject mult(NumericObject a, NumericObject b);

    NumericObject amult(NumericObject a, NumericObject b);

    NumericObject sub(NumericObject a, NumericObject b);

    NumericObject div(NumericObject a, NumericObject b);
    
    NumericObject pow(NumericObject a, NumericObject b);
    
    NumericObject apow(NumericObject a, NumericObject b);

    NumericObject eq(NumericObject a, NumericObject b);

    NumericObject neq(NumericObject a, NumericObject b);

    NumericObject gt(NumericObject a, NumericObject b);

    NumericObject ge(NumericObject a, NumericObject b);

    NumericObject le(NumericObject a, NumericObject b);

    NumericObject lt(NumericObject a, NumericObject b);
}
