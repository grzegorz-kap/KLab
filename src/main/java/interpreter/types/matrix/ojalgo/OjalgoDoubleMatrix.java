package interpreter.types.matrix.ojalgo;

import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.DoubleScalar;
import interpreter.types.scalar.Scalar;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;

public class OjalgoDoubleMatrix extends OjalgoAbstractMatrix<Double> {

    public OjalgoDoubleMatrix(MatrixStore<Double> store) {
        super(NumericType.MATRIX_DOUBLE);
        setFactory(PrimitiveDenseStore.FACTORY);
        setLazyStore(store);
    }

    @Override
    public ObjectData copyObjectData() {
        return new OjalgoDoubleMatrix(getLazyStore().copy());
    }

    @Override
    protected Scalar createScalar(Number number) {
        return new DoubleScalar(number);
    }
}
