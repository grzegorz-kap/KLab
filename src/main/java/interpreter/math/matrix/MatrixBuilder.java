package interpreter.math.matrix;

public interface MatrixBuilder<T extends Number> {

    MatrixBuilder<T> appendRight(Matrix<T> matrix);

    MatrixBuilder<T> appendRight(T scalar);

    MatrixBuilder<T> appendBelow(Matrix<T> matrix);

    MatrixBuilder<T> appendBelow(T scalar);

    Matrix<T> build();

}
