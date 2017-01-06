package com.klab.interpreter.functions.matrix.generation;

import com.klab.interpreter.types.matrix.Matrix;

public interface MatrixCreator {
    Matrix<Double> create(int rows, int columns);
}
