package interpreter.types.matrix.ojalgo;

import interpreter.parsing.model.NumericType;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;

public class OjalgoMatrix<T extends Number> extends OjalgoAbstractMatrix<T> {

    public <P extends PhysicalStore<T>> OjalgoMatrix(P matrixStore) {
        super(NumericType.MATRIX_DOUBLE);
        this.setMatrixStore(matrixStore);
        this.setLazyStore(matrixStore);
    }

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
}
