package interpreter.types.scalar;

import interpreter.types.Negable;
import interpreter.types.NumericObject;
import interpreter.types.Sizeable;

public interface Scalar<T extends Number> extends Sizeable, NumericObject, Negable<Scalar<T>> {
	T getValue();

	int getIntOrThrow();
}
