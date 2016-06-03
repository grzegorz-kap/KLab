package com.klab.interpreter.service.functions.math;

import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.NumericType;

public interface MathFunctions {
    NumericType supports();

    NumericObject sqrt(NumericObject value);

    NumericObject inv(NumericObject value) throws Exception;

    NumericObject sin(NumericObject value);

    NumericObject det(NumericObject value);

    NumericObject tan(NumericObject value);

    NumericObject cos(NumericObject value);

    NumericObject log(NumericObject a, NumericObject b);

    NumericObject log(NumericObject a);

    NumericObject log10(NumericObject a);
}
