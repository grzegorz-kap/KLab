package com.klab.interpreter.types.scalar;

import com.klab.interpreter.types.*;

public interface Scalar<T extends Number> extends Sizeable, NumericObject, Negable<Scalar<T>>, Editable<T>, EditSupportable<T> {
    T getValue();

    int getIntOrThrow();
}
