package com.klab.interpreter.types.foriterator;

import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.function.NullaryFunction;
import org.ojalgo.matrix.store.PhysicalStore;

public class OjalgoMultiRowForIterator<N extends Number> extends OjalgoAbstractForIterator<N> {
    private PhysicalStore.Factory<N, ? extends PhysicalStore<N>> factory;
    private PhysicalStore<N> sourceStore;
    private long totalSize;
    private OjalgoMatrixCreator<N> matrixCreator;

    private NullaryFunction<N> nextColumnFiller = new NullaryFunction<N>() {
        @Override
        public double doubleValue() {
            return sourceStore.get(currentColumn++).doubleValue();
        }

        @Override
        public N invoke() {
            return sourceStore.get(currentColumn++);
        }
    };

    public OjalgoMultiRowForIterator(OjalgoAbstractMatrix<N> data) {
        super(data);
        totalSize = data.getColumns() * data.getRows();
        factory = data.getMatrixStore().factory();
        sourceStore = data.getMatrixStore();
        matrixCreator = data::create;
    }

    @Override
    public ObjectData getNext() {
        return matrixCreator.create(factory.makeFilled(rows, 1, nextColumnFiller));
    }

    @Override
    public boolean hasNext() {
        return currentColumn < totalSize;
    }
}
