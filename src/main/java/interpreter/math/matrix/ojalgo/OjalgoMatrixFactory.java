package interpreter.math.matrix.ojalgo;

import interpreter.math.matrix.Matrix;
import interpreter.math.matrix.MatrixBuilder;
import interpreter.math.matrix.MatrixFactory;
import org.ojalgo.matrix.store.PhysicalStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.springframework.stereotype.Service;

@Service
public class OjalgoMatrixFactory implements MatrixFactory {

    private static final PhysicalStore.Factory<Double, PrimitiveDenseStore> DOUBLE_FACTORY = PrimitiveDenseStore.FACTORY;

    @Override
    public MatrixBuilder<Double> createDoubleBuilder() {
        return new OjalgoMatrixBuilder<>(DOUBLE_FACTORY);
    }

    @Override
    public Matrix<Double> createDouble(int rows, int columns) {
        return new OjalgoMatrix<>(DOUBLE_FACTORY.makeZero(0, 0));
    }
}
