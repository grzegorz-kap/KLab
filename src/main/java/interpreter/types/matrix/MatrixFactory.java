package interpreter.types.matrix;

public interface MatrixFactory {

    MatrixBuilder<Double> createDoubleBuilder();

    Matrix<Double> createDouble(int rows, int columns);
}
