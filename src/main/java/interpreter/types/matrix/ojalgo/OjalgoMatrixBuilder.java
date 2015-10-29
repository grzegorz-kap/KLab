package interpreter.types.matrix.ojalgo;

import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixBuilder;
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
        createIfEmpty(matrix.getRows(), 0);
        builder.right(((OjalgoMatrix<T>) matrix).getMatrixStore());
        return this;
    }

    @Override
    public MatrixBuilder<T> appendRight(T scalar) {
        createIfEmpty(1, 0);
        builder.right(scalar);
        return this;
    }

    @Override
    public OjalgoMatrixBuilder<T, I> appendBelow(Matrix<T> matrix) {
        createIfEmpty(0, matrix.getColumns());
        builder.below(((OjalgoMatrix<T>) matrix).getMatrixStore());
        return this;
    }

    @Override
    public MatrixBuilder<T> appendBelow(T scalar) {
        createIfEmpty(0, 1);
        builder.below(scalar);
        return this;
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
