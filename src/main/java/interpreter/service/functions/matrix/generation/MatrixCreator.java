package interpreter.service.functions.matrix.generation;

import interpreter.types.matrix.Matrix;

public interface MatrixCreator {
	Matrix<Double> create(int rows, int columns);
}
