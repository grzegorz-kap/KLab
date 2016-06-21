package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.MatrixBuilder;
import org.ojalgo.matrix.store.PhysicalStore;

import java.util.Objects;

abstract class OjalgoAbstractMatrixBuilder<T extends Number> implements MatrixBuilder<T> {
    protected PhysicalStore.Factory<T, ? extends PhysicalStore<T>> factory;
    protected PhysicalStore.LogicalBuilder<T> builder;

    OjalgoAbstractMatrixBuilder(PhysicalStore.Factory<T, ? extends PhysicalStore<T>> factory) {
        this.factory = factory;
    }

    protected abstract T convert(Number number);
    protected abstract OjalgoAbstractMatrix<T> convert(Matrix<? extends Number> matrix);

    @Override
    public OjalgoAbstractMatrixBuilder<T> appendRight(Matrix<Number> matrix) {
        createIfEmpty(matrix.getRows(), 0);
        builder.right(convert(matrix).getMatrixStore());
        return this;
    }

    @Override
    public MatrixBuilder<T> appendRight(Number scalar) {
        createIfEmpty(1, 0);
        builder.right(convert(scalar));
        return this;
    }

    @Override
    public OjalgoAbstractMatrixBuilder<T> appendBelow(Matrix<Number> matrix) {
        createIfEmpty(0, matrix.getColumns());
        builder.below(convert(matrix).getMatrixStore());
        return this;
    }

    @Override
    public MatrixBuilder<T> appendBelow(Number scalar) {
        createIfEmpty(0, 1);
        builder.below(convert(scalar));
        return this;
    }

    private void createIfEmpty(int rowsNumber, int colsNumber) {
        if (Objects.isNull(builder)) {
            builder = factory.builder().makeZero(rowsNumber, colsNumber);
        }
    }
}
