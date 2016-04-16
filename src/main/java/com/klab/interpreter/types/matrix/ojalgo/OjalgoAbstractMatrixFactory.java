package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.MatrixFactory;
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

    @Override
    public Matrix<N> createRange(Number start, Number step, Number stop) {
        double j = start.doubleValue();
        double i = step.doubleValue();
        double k = stop.doubleValue();
        double temp = (k - j) / i;
        long m = (long) (temp >= 0.0 ? Math.floor(temp) : Math.ceil(temp));
        if (i == 0 || m == 0 || i > 0 && j > k || i < 0 && j < k) {
            return create(factory.makeZero(0, 0));
        }
        PhysicalStore<N> store = factory.makeZero(1, (m + 1));
        for (long mult = 0; mult <= m; mult++) {
            store.set(mult, j + i * mult);
        }
        return create(store);
    }

    public void setFactory(PhysicalStore.Factory<N, ? extends PhysicalStore<N>> factory) {
        this.factory = factory;
    }

    public void setOnesGenerator(NullaryFunction<N> onesGenerator) {
        this.onesGenerator = onesGenerator;
    }
}
