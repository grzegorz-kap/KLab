package interpreter.core.arithmetic.scalar;

import interpreter.types.scalar.Scalar;

public interface ScalarCreator<T extends Number> {
    Scalar<T> create(T value);
}
