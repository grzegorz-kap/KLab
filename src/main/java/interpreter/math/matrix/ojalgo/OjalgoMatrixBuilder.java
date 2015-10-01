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
        createIfEmpty(1, 0);
        builder.right(scalar);
        return this;
    }

    @Override
    public OjalgoMatrixBuilder<T, I> appendBelow(Matrix<T> matrix) {
        createIfEmpty(0, matrix.getColumnsCount());
        builder.below(((OjalgoMatrix) matrix).getMatrixStore());
        return this;
    }

    @Override
    public MatrixBuilder<T> appendBelow(T scalar) {
        return null;
    }

    @Override
    public OjalgoMatrix<T> build() {
        return new OjalgoMatrix<>(builder.copy());
    }

    private void createIfEmpty(long rowsNumber, long colsNumber) {
        if (Objects.isNull(builder)) {
            builder = factory.makeZero(rowsNumber, colsNumber).builder();
        }
    }

}
