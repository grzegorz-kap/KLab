package interpreter.core.arithmetic.factory;

import interpreter.core.arithmetic.NumericObjectsOperator;
import interpreter.types.NumericType;

public interface NumericObjectsOperatorFactory {
    NumericObjectsOperator getOperator(NumericType type);
}
