package interpreter.types.matrix.ojalgo;

import interpreter.types.AddressIterator;
import interpreter.types.Addressable;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.foriterator.ForIterator;
import interpreter.types.scalar.DoubleScalar;
import interpreter.types.scalar.Scalar;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;

public class OjalgoDoubleMatrix extends OjalgoAbstractMatrix<Double> implements Addressable {

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
    public OjalgoAbstractMatrix<Double> create(MatrixStore<Double> matrixStore) {
        return new OjalgoDoubleMatrix(matrixStore);
    }

    @Override
    protected Scalar createScalar(Number number) {
        return new DoubleScalar(number);
    }

    @Override
    public AddressIterator getAddressIterator() {
        return new OjalgoAddressIterator<>(getLazyStore());
    }
}
