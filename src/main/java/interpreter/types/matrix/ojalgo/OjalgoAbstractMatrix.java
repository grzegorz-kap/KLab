package interpreter.types.matrix.ojalgo;

import interpreter.types.AbstractNumericObject;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.Sizeable;
import interpreter.types.matrix.Matrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;

import java.util.function.Consumer;

public abstract class OjalgoAbstractMatrix<T extends Number> extends AbstractNumericObject implements Matrix<T>, Sizeable {
    private PhysicalStore<T> matrixStore;
    private MatrixStore<T> lazyStore;

    public OjalgoAbstractMatrix(NumericType numericType) {
        super(numericType);
    }

    @Override
    public ObjectData copyObjectData() {
        OjalgoMatrix<T> ojalgoMatrix = new OjalgoMatrix<>(getMatrixStore().copy());
        ojalgoMatrix.setNumericType(getNumericType());
        return ojalgoMatrix;
    }

    @Override
    public T get(long m, long n) {
        return getLazyStore().get(m, n);
    }

    @Override
    public T get(int m) {
        return getLazyStore().get(m);
    }

    @Override
    public void set(int m, int n, T value) {
        getMatrixStore().set(m, n, value);
    }

    @Override
    public String toString() {
        return OjalgoMatrixPrinter.toString(getMatrixStore());
    }

    @Override
    public long getRows() {
        return getLazyStore().countRows();
    }

    @Override
    public long getColumns() {
        return getLazyStore().countColumns();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        getMatrixStore().forEach(action);
    }

    public PhysicalStore<T> getMatrixStore() {
        if (matrixStore == null) {
            matrixStore = getLazyStore().copy();
        }
        return matrixStore;
    }

    public MatrixStore<T> getLazyStore() {
        return lazyStore;
    }

    public void setLazyStore(MatrixStore<T> lazyStore) {
        this.lazyStore = lazyStore;
    }
}
