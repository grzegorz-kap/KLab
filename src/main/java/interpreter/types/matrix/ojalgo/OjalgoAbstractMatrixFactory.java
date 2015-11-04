package interpreter.types.matrix.ojalgo;

import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixFactory;
import org.ojalgo.function.NullaryFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.random.Uniform;

public abstract class OjalgoAbstractMatrixFactory<N extends Number> implements MatrixFactory<N> {

    private PhysicalStore.Factory<N, ? extends PhysicalStore<N>> factory;
    private NullaryFunction<N> onesGenerator;
    private Uniform randGenerator = new Uniform();

    protected abstract Matrix<N> create(MatrixStore<N> store);

    @Override
    public Matrix<N> create(int rows, int columns) {
        return create(factory.makeZero(rows, columns));
    }

    @Override
    public Matrix<N> rand(int rows, int columns) {
        return create(factory.makeFilled(rows, columns, randGenerator));
    }

    @Override
    public Matrix<N> eye(int rows, int cols) {
        return create(factory.makeEye(rows, cols));
    }

    @Override
    public Matrix<N> ones(int rows, int cols) {
        return create(factory.makeFilled(rows, cols, onesGenerator));
    }

    @Override
    public Matrix<N> zeros(int rows, int columns) {
        return create(factory.makeZero(rows, columns));
    }

    public void setFactory(PhysicalStore.Factory<N, ? extends PhysicalStore<N>> factory) {
        this.factory = factory;
    }

    public void setOnesGenerator(NullaryFunction<N> onesGenerator) {
        this.onesGenerator = onesGenerator;
    }
}
