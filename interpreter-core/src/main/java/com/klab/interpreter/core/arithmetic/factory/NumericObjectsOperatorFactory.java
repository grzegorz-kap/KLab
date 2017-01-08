package com.klab.interpreter.core.arithmetic.factory;

import com.klab.interpreter.core.arithmetic.NumericObjectsOperator;
import com.klab.interpreter.types.NumericType;

public interface NumericObjectsOperatorFactory {
    NumericObjectsOperator getOperator(NumericType type);
}
