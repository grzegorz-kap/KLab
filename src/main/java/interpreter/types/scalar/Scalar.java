package interpreter.types.scalar;

import interpreter.types.NumericObject;
import interpreter.types.Sizeable;

public interface Scalar extends Sizeable, NumericObject {

    Number getValue();

    int getIntOrThrow();

}
