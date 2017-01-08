package com.klab.interpreter.functions.interpolation;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;

public interface LagrangeInterpolator<N extends Number> {
    Matrix<N> interpolate(Matrix<N> x, Matrix<N> y, int degree);

    NumericType supports();
}