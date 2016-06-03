package com.klab.interpreter.types.matrix;

import com.klab.interpreter.service.LUResult;
import com.klab.interpreter.types.*;
import com.klab.interpreter.types.foriterator.ForIterable;
import com.klab.interpreter.types.scalar.Scalar;

import java.util.function.Consumer;

public interface Matrix<T extends Number> extends
        Scalar<T>,
        NumericObject,
        Sizeable,
        ForIterable,
        Indexable,
        Negable<T>,
        Editable<T>,
        EditSupportable<T> {

    T get(long m, long n);

    T get(int m);

    void set(int m, int n, T value);

    void forEach(Consumer<? super T> action);

    T getNumber(int i);

    LUResult lu();
}
