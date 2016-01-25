package interpreter.types.scalar;

import interpreter.types.*;

public interface Scalar<T extends Number> extends Sizeable, NumericObject, Negable<Scalar<T>>, Editable<T>, EditSupportable<T> {
    T getValue();

    int getIntOrThrow();
}
