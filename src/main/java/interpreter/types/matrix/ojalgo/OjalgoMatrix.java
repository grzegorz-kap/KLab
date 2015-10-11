package interpreter.types.matrix.ojalgo;

import interpreter.parsing.model.NumericType;
import interpreter.types.AbstractNumericObject;
import interpreter.types.matrix.Matrix;
import org.ojalgo.matrix.store.PhysicalStore;

public class OjalgoMatrix<T extends Number> extends AbstractNumericObject implements Matrix<T> {

    private PhysicalStore<T> matrixStore;

    public <P extends PhysicalStore<T>> OjalgoMatrix(P matrixStore) {
        super(NumericType.MATRIX_DOUBLE);
        this.matrixStore = matrixStore;
    }

    @Override
    public T getValueAt(int m, int n) {
        return matrixStore.get(m, n);
    }

    @Override
    public void setValueAt(int m, int n, T value) {
        matrixStore.set(m, n, value);
    }

    @Override
    public long getRowsCount() {
        return matrixStore.countRows();
    }

    @Override
    public long getColumnsCount() {
        return matrixStore.countColumns();
    }

    public PhysicalStore<T> getMatrixStore() {
        return matrixStore;
    }

    @Override
    public String toString() {
        return OjalgoMatrixPrinter.toString(matrixStore);
    }
}
