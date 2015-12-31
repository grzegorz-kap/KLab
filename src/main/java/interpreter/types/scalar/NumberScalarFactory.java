package interpreter.types.scalar;

import interpreter.types.NumericObject;

public interface NumberScalarFactory {
    NumericObject getDouble(double value);
    NumericObject getDouble(String value);
    Scalar<Double> getDouble(boolean value);
}
