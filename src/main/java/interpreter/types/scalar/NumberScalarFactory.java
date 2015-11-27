package interpreter.types.scalar;

import interpreter.types.NumericObject;

public interface NumberScalarFactory {
    NumericObject getDouble(String value);
}
