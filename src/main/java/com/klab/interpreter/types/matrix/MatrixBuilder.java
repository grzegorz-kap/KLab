package com.klab.interpreter.types.matrix;

public interface MatrixBuilder<T extends Number> {

    MatrixBuilder<T> appendRight(Matrix<Number> matrix);

    MatrixBuilder<T> appendRight(Number scalar);

    MatrixBuilder<T> appendBelow(Matrix<Number> matrix);

    MatrixBuilder<T> appendBelow(Number scalar);

    Matrix<T> build();

}
