package interpreter.math.matrix.ojalgo;

import interpreter.math.matrix.Matrix;
import org.ojalgo.matrix.store.PhysicalStore;

public class OjalgoMatrix<T extends Number> implements Matrix<T> {

    private PhysicalStore<T> matrixStore;

    public OjalgoMatrix(PhysicalStore<T> matrixStore) {
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
