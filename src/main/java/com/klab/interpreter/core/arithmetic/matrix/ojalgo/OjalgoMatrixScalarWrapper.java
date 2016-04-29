package com.klab.interpreter.core.arithmetic.matrix.ojalgo;

import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import org.ojalgo.access.Access1D;
import org.ojalgo.function.VoidFunction;
import org.ojalgo.function.aggregator.Aggregator;
import org.ojalgo.matrix.store.ElementsConsumer;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.scalar.Scalar;
import org.ojalgo.type.context.NumberContext;

public class OjalgoMatrixScalarWrapper<N extends Number> implements MatrixStore<N> {
    private final PhysicalStore.Factory<N, ?> factory;
    private N value;
    private long rows = -1;
    private long cols = -1;

    public OjalgoMatrixScalarWrapper(MatrixStore<N> wrap) {
        this.value = wrap.get(0);
        this.factory = wrap.factory();
    }

    public OjalgoMatrixScalarWrapper(OjalgoAbstractMatrix<N> wrap, OjalgoAbstractMatrix<N> second) {
        this(wrap.getLazyStore());
        rows = second.getRows();
        cols = second.getColumns();
    }

    @Override
    public PhysicalStore.Factory<N, ?> factory() {
        return factory;
    }

    @Override
    public void supplyTo(ElementsConsumer<N> elementsConsumer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public N get(long l, long l1) {
        return value;
    }

    @Override
    public double doubleValue(long l, long l1) {
        return value.doubleValue();
    }

    @Override
    public long countColumns() {
        return cols;
    }

    @Override
    public long countRows() {
        return rows;
    }

    @Override
    public N aggregateAll(Aggregator aggregator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Builder<N> builder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PhysicalStore<N> copy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(MatrixStore<N> matrixStore, NumberContext numberContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public N multiplyBoth(Access1D<N> access1D) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MatrixStore<N> multiplyLeft(Access1D<N> access1D) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Scalar<N> toScalar(long l, long l1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MatrixStore<N> transpose() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAbsolute(long l, long l1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSmall(long l, long l1, double v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAbsolute(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSmall(long l, double v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MatrixStore<N> conjugate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitColumn(long l, long l1, VoidFunction<N> voidFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitDiagonal(long l, long l1, VoidFunction<N> voidFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitRow(long l, long l1, VoidFunction<N> voidFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitAll(VoidFunction<N> voidFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitRange(long l, long l1, VoidFunction<N> voidFunction) {
        throw new UnsupportedOperationException();
    }
}
