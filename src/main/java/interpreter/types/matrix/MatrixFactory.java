package interpreter.types.matrix;

import interpreter.types.NumericType;

public interface MatrixFactory<N extends Number> {

    MatrixBuilder<N> builder();

    Matrix<N> create(int rows, int columns);

    Matrix<N> rand(int rows, int columns);

    Matrix<N> eye(int rows, int cols);

    Matrix<N> ones(int rows, int cols);

    Matrix<N> zeros(int rows, int columns);

    Matrix<N> createRange(Number start, Number step, Number stop);

    NumericType supports();
}
