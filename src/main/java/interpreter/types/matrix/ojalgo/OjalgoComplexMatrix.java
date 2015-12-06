package interpreter.types.matrix.ojalgo;

import static interpreter.commons.exception.InterpreterCastException.COMPLEX_LOGICALS;

import org.ojalgo.matrix.store.ComplexDenseStore;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.types.AddressIterator;
import interpreter.types.Addressable;
import interpreter.types.Negable;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.Matrix;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.Scalar;

public class OjalgoComplexMatrix extends OjalgoAbstractMatrix<ComplexNumber> implements Addressable {
	private static final Negable.UnaryNagate<ComplexNumber> NEGATE_FUN = new Negable.UnaryNagate<ComplexNumber>() {
		@Override
		public ComplexNumber invoke(ComplexNumber arg) {
			return new ComplexNumber(arg.getReal() != 0 || arg.getImaginary() != 0 ? 0.0D : 1.D);
		}
	};

	public OjalgoComplexMatrix(MatrixStore<ComplexNumber> store) {
		super(NumericType.COMPLEX_MATRIX);
		setLazyStore(store);
		setFactory(ComplexDenseStore.FACTORY);
	}

	@Override
	public Negable<Matrix<ComplexNumber>> negate() {
		return new OjalgoComplexMatrix(getLazyStore().operateOnAll(NEGATE_FUN));
	}

	@Override
	public OjalgoAbstractMatrix<ComplexNumber> create(MatrixStore<ComplexNumber> matrixStore) {
		return new OjalgoComplexMatrix(matrixStore);
	}

	@Override
	public boolean isTrue() {
		for (ComplexNumber value : getMatrixStore()) {
			if (value.getImaginary() != 0.0D) {
				throw new InterpreterCastException(COMPLEX_LOGICALS);
			}
			if (value.getReal() == 0.0D) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ObjectData copyObjectData() {
		return new OjalgoComplexMatrix(getLazyStore().copy());
	}

	@Override
	protected Scalar<ComplexNumber> createScalar(Number number) {
		return new ComplexScalar(number);
	}

	@Override
	public AddressIterator getAddressIterator() {
		return new OjalgoAddressIterator<>(getLazyStore());
	}
}
