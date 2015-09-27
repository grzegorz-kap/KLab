package interpreter.math.matrix.ojalgo;

import interpreter.math.matrix.Matrix;
import interpreter.math.matrix.MatrixBuilder;
import org.ojalgo.matrix.store.PhysicalStore;

public class OjalgoMatrixBuilder<T extends Number, I extends PhysicalStore<T>> implements MatrixBuilder<T> {

    private PhysicalStore.Builder<T> builder;

    public OjalgoMatrixBuilder(PhysicalStore.Factory<T, I> factory) {
        builder = factory.makeZero(0, 0).builder();
    }

    @Override
    public OjalgoMatrixBuilder<T, I> appendRight(Matrix<T> matrix) {
        return null;
    }

    @Override
    public OjalgoMatrixBuilder<T, I> appendBelow(Matrix<T> matrix) {
        return null;
    }

    @Override
    public OjalgoMatrix<T> build() {
        return null;
    }
}
