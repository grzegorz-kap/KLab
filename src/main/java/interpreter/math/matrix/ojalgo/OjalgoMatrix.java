package interpreter.math.matrix.ojalgo;

import interpreter.commons.ObjectData;
import interpreter.math.matrix.Matrix;
import org.ojalgo.matrix.store.PhysicalStore;

public class OjalgoMatrix<T extends Number> extends ObjectData implements Matrix<T> {

    private PhysicalStore<T> matrixStore;

    public OjalgoMatrix(PhysicalStore<T> matrixStore) {
        this.matrixStore = matrixStore;
    }

    @Override
    public T get(int m, int n) {
        return matrixStore.get(m, n);
    }

    @Override
    public void set(int m, int n, T value) {
        matrixStore.set(m, n, value);
    }

}
