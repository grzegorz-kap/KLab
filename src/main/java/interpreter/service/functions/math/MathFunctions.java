package interpreter.service.functions.math;

import interpreter.parsing.model.NumericType;
import interpreter.types.NumericObject;

public interface MathFunctions {

    NumericType supports();

    NumericObject sqrt(NumericObject value);

    NumericObject inv(NumericObject value) throws Exception;

    NumericObject sin(NumericObject value);

    NumericObject det(NumericObject value);

    NumericObject tan(NumericObject value);

    NumericObject cos(NumericObject value);
}
