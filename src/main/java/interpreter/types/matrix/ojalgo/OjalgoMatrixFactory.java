package interpreter.types.matrix.ojalgo;

import org.ojalgo.function.NullaryFunction;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ojalgo.random.Uniform;
import org.springframework.stereotype.Service;

import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixBuilder;
import interpreter.types.matrix.MatrixFactory;

@Service
public class OjalgoMatrixFactory implements MatrixFactory {

	private static final PhysicalStore.Factory<Double, PrimitiveDenseStore> DOUBLE_FACTORY = PrimitiveDenseStore.FACTORY;

	private NullaryFunction<Double> randGenerator = new Uniform();
	private NullaryFunction<Double> onesGenerator = new OnesFunctionFiller();

	@Override
	public MatrixBuilder<Double> createDoubleBuilder() {
		return new OjalgoMatrixBuilder<>(DOUBLE_FACTORY);
	}

	@Override
	public Matrix<Double> createDouble(int rows, int columns) {
		return new OjalgoMatrix<>(DOUBLE_FACTORY.makeZero(rows, columns));
	}

	@Override
	public Matrix<Double> createRandDouble(int rows, int columns) {
		return new OjalgoMatrix<>(DOUBLE_FACTORY.makeFilled(rows, columns, randGenerator));
	}

	public void setRandGenerator(NullaryFunction<Double> randGenerator) {
		this.randGenerator = randGenerator;
	}

	@Override
	public Matrix<Double> createEyeDouble(int rows, int cols) {
		return new OjalgoMatrix<>(DOUBLE_FACTORY.makeEye(rows, cols));
	}

	@Override
	public Matrix<Double> createOnesDouble(int rows, int cols) {
		return new OjalgoMatrix<>(DOUBLE_FACTORY.makeFilled(rows, cols, onesGenerator));
	}
	
	@Override
	public Matrix<Double> createZerosDouble(int rows, int columns) {
		return new OjalgoMatrix<>(DOUBLE_FACTORY.makeZero(rows, columns));
	}

	private final class OnesFunctionFiller implements NullaryFunction<Double> {
		@Override
		public double doubleValue() {
			return 1D;
		}

		@Override
		public Double invoke() {
			return 1D;
		}
	}
}
