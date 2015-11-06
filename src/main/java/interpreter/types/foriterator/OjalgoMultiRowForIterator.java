package interpreter.types.foriterator;

import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.function.NullaryFunction;
import org.ojalgo.matrix.store.PhysicalStore;

public class OjalgoMultiRowForIterator<N extends Number> extends OjalgoAbstractForIterator<N> {
    private PhysicalStore.Factory<N, ? extends PhysicalStore<N>> factory;
    private PhysicalStore<N> source;
    private long totalSize;

    private NullaryFunction<N> nextColumnFiller = new NullaryFunction<N>() {
        @Override
        public double doubleValue() {
            return source.get(currentColumn++).doubleValue();
        }

        @Override
        public N invoke() {
            return source.get(currentColumn++);
        }
    };

    public OjalgoMultiRowForIterator(OjalgoMatrix<N> data) {
        super(data);
        totalSize = data.getColumns() * data.getRows();
        factory = data.getMatrixStore().factory();
        source = data.getMatrixStore();
    }

    @Override
    public ObjectData getNext() {
        OjalgoMatrix<N> nOjalgoMatrix = new OjalgoMatrix<>(factory.makeFilled(rows, 1, nextColumnFiller));
        nOjalgoMatrix.setNumericType(data.getNumericType());
        return nOjalgoMatrix;
    }

    @Override
    public boolean hasNext() {
        return currentColumn < totalSize;
    }
}
