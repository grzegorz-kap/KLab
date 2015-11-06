package interpreter.types.matrix.ojalgo;

import interpreter.types.NumericType;
import interpreter.types.foriterator.ForIterator;
import interpreter.types.foriterator.OjalgoForIteratorFactory;
import org.ojalgo.matrix.store.MatrixStore;

public class OjalgoMatrix<T extends Number> extends OjalgoAbstractMatrix<T> {

    public OjalgoMatrix(MatrixStore<T> store) {
        super(NumericType.MATRIX_DOUBLE);
        setLazyStore(store);
    }

    @Override
    public boolean isTrue() {
        for (T value : getMatrixStore()) {
            if (value.doubleValue() == 0.0D) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ForIterator getForIterator() {
        return OjalgoForIteratorFactory.create(this);
    }
}
