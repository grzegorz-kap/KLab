package interpreter.types.matrix;

public interface MatrixFactory {

	MatrixBuilder<Double> createDoubleBuilder();

	Matrix<Double> createDouble(int rows, int columns);

	Matrix<Double> createRandDouble(int rows, int columns);

	Matrix<Double> createEyeDouble(int rows, int cols);
	
	Matrix<Double> createOnesDouble(int rows, int cols);
	
	Matrix<Double> createZerosDouble(int rows, int columns);
}
