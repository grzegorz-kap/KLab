package interpreter.types.matrix.ojalgo;

import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;

import interpreter.types.AddressIterator;
import interpreter.types.Addressable;
import interpreter.types.Negable;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.DoubleScalar;
import interpreter.types.scalar.Scalar;

public class OjalgoDoubleMatrix extends OjalgoAbstractMatrix<Double> implements Addressable {
	private static final Negable.UnaryNagate<Double> NEG_FUN  = new Negable.UnaryNagate<Double>() {
		@Override
		public Double invoke(Double arg) {
			return invoke(arg.doubleValue());
		}
	};

    public OjalgoDoubleMatrix(MatrixStore<Double> store) {
        super(NumericType.MATRIX_DOUBLE);
        setFactory(PrimitiveDenseStore.FACTORY);
        setLazyStore(store);
    }
    
    @Override
    public Negable<Matrix<Double>> negate() {
    	return new OjalgoDoubleMatrix(getLazyStore().operateOnAll(NEG_FUN));
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
    protected Scalar<Double> createScalar(Number number) {
        return new DoubleScalar(number);
    }

    @Override
    public AddressIterator getAddressIterator() {
        return new OjalgoAddressIterator<>(getLazyStore());
    }
}
