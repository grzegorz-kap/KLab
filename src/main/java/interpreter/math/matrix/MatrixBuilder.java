package interpreter.math.matrix;

public interface MatrixBuilder<T extends Number> {

    MatrixBuilder<T> appendRight(Matrix<T> matrix);

    MatrixBuilder<T> appendBelow(Matrix<T> matrix);

    Matrix<T> build();

}
