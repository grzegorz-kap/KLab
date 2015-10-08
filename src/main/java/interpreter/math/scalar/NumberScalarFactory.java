package interpreter.math.scalar;

import interpreter.math.NumericObject;

public interface NumberScalarFactory {

    NumericObject getDouble(Double value);
}
