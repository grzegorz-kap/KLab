package interpreter.math.matrix.ojalgo;

import interpreter.math.matrix.Matrix;
import interpreter.math.matrix.MatrixBuilder;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;

public class OjalgoMatrixBuilder<T extends Number> implements MatrixBuilder<T> {

    public static void main(String[] args) {
        PhysicalStore.Factory<Double, PrimitiveDenseStore> factory = PrimitiveDenseStore.FACTORY;

        PrimitiveDenseStore store = factory.makeZero(0, 0);
        MatrixStore.Builder<Double> builder = store.builder();
        builder.right(10.0, 23.0, 30.0);
        MatrixStore<Double> store2 = builder.build();
    }

    @Override
    public OjalgoMatrixBuilder<T> appendRight(Matrix<T> matrix) {

        return null;
    }

    @Override
    public OjalgoMatrixBuilder<T> appendBelow(Matrix<T> matrix) {
        return null;
    }

    @Override
    public OjalgoMatrix<T> build() {
        return null;
    }
}
