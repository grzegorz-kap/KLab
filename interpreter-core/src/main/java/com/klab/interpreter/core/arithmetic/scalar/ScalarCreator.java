package com.klab.interpreter.core.arithmetic.scalar;

import com.klab.interpreter.types.scalar.Scalar;

public interface ScalarCreator<T extends Number> {
    Scalar<T> create(T value);
}
