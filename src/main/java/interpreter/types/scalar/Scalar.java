package interpreter.types.scalar;

import interpreter.types.NumericObject;
import interpreter.types.Sizeable;

public interface Scalar<T extends Number> extends Sizeable, NumericObject {
	T getValue();

	int getIntOrThrow();
}
