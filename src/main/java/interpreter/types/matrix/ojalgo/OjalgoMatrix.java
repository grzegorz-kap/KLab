package interpreter.types.matrix.ojalgo;

import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.DoubleScalar;
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
    public ObjectData copyObjectData() {
        return new OjalgoMatrix<>(getLazyStore().copy());
    }

    @Override
    public ObjectData get(int row, int column) {
        return new DoubleScalar(getLazyStore().get(row - 1, column - 1));
    }

    @Override
    public ObjectData get(int cell) {
        return new DoubleScalar(getLazyStore().get(cell - 1));
    }
}
