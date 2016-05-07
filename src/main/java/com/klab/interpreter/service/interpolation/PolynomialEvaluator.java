package com.klab.interpreter.service.interpolation;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;

public interface PolynomialEvaluator<N extends Number> {
    Matrix<N> evaluate(Matrix<N> p, Matrix<N> x);

    NumericType supports();
}
