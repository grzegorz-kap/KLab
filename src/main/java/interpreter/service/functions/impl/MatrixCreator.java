package interpreter.service.functions.impl;

import interpreter.types.matrix.Matrix;

public interface MatrixCreator {
	Matrix<Double> create(int rows, int columns);
}
