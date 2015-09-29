package interpreter.math.matrix.ojalgo;

import interpreter.math.matrix.Matrix;
import interpreter.math.matrix.MatrixBuilder;
import org.ojalgo.matrix.store.PhysicalStore;

import java.util.Objects;

public class OjalgoMatrixBuilder<T extends Number, I extends PhysicalStore<T>> implements MatrixBuilder<T> {

    private PhysicalStore.Factory<T, I> factory;
    private PhysicalStore.Builder<T> builder;

    public OjalgoMatrixBuilder(PhysicalStore.Factory<T, I> factory) {
        this.factory = factory;
    }

    @Override
    public OjalgoMatrixBuilder<T, I> appendRight(Matrix<T> matrix) {
        return null;
    }

    @Override
    public MatrixBuilder<T> appendRight(T scalar) {
        createRowsIfEmpty(1);
        builder.right(scalar);
        return this;
    }

    private void createRowsIfEmpty(int rowsNumber) {
        if (Objects.isNull(builder)) {
            builder = factory.makeZero(rowsNumber, 0).builder();
        }
    }

    @Override
    public OjalgoMatrixBuilder<T, I> appendBelow(Matrix<T> matrix) {
        return null;
    }

    @Override
    public MatrixBuilder<T> appendBelow(T scalar) {
        return null;
    }

    @Override
    public OjalgoMatrix<T> build() {
        return new OjalgoMatrix<>(builder.copy());
    }

}
