package interpreter.core.arithmetic.matrix.ojalgo;

import org.ojalgo.function.ComplexFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.stereotype.Component;

import interpreter.core.arithmetic.NumericObjectsComparator;
import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;

@Component
public class CompexOjalgoMatrixComparator extends AbstractOjalgoMatrixBinaryOperator<ComplexNumber>
		implements NumericObjectsComparator {

	@Override
	public NumericType getSupportedType() {
		return NumericType.COMPLEX_MATRIX;
	}

	@Override
	protected MatrixStore<ComplexNumber> operate(OjalgoMatrix<ComplexNumber> first,
			OjalgoMatrix<ComplexNumber> second) {
		return null;
	}

	@Override
	public ObjectData eq(ObjectData a, ObjectData b) {
		return operate(a, b, this::eq);
	}

	@Override
	public ObjectData neq(ObjectData a, ObjectData b) {
		return operate(a, b, this::neq);
	}

	@Override
	public ObjectData gt(ObjectData a, ObjectData b) {
		return operate(a, b, this::gt);
	}

	@Override
	public ObjectData ge(ObjectData a, ObjectData b) {
		return operate(a, b, this::ge);
	}

	@Override
	public ObjectData le(ObjectData a, ObjectData b) {
		return operate(a, b, this::le);
	}

	@Override
	public ObjectData lt(ObjectData a, ObjectData b) {
		return operate(a, b, this::lt);
	}

	private MatrixStore<ComplexNumber> eq(OjalgoMatrix<ComplexNumber> first, OjalgoMatrix<ComplexNumber> second) {
		return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::eq, second.getLazyStore());
	}

	private MatrixStore<ComplexNumber> neq(OjalgoMatrix<ComplexNumber> first, OjalgoMatrix<ComplexNumber> second) {
		return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::neq, second.getLazyStore());
	}

	private MatrixStore<ComplexNumber> gt(OjalgoMatrix<ComplexNumber> first, OjalgoMatrix<ComplexNumber> second) {
		return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::gt, second.getLazyStore());
	}

	private MatrixStore<ComplexNumber> ge(OjalgoMatrix<ComplexNumber> first, OjalgoMatrix<ComplexNumber> second) {
		return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::ge, second.getLazyStore());
	}

	private MatrixStore<ComplexNumber> le(OjalgoMatrix<ComplexNumber> first, OjalgoMatrix<ComplexNumber> second) {
		return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::le, second.getLazyStore());
	}

	private MatrixStore<ComplexNumber> lt(OjalgoMatrix<ComplexNumber> first, OjalgoMatrix<ComplexNumber> second) {
		return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::lt, second.getLazyStore());
	}

	private ComplexNumber eq(ComplexNumber value, ComplexNumber second) {
		return new ComplexNumber(value.equals(second) ? 1.0D : 0.0D);
	}

	private ComplexNumber neq(ComplexNumber value, ComplexNumber second) {
		return new ComplexNumber(value.equals(second) ? 0.0D : 1.0D);
	}

	private ComplexNumber gt(ComplexNumber value, ComplexNumber second) {
		return new ComplexNumber(value.compareTo(second) == 1 ? 1D : 0D);
	}

	private ComplexNumber ge(ComplexNumber value, ComplexNumber second) {
		return new ComplexNumber(value.compareTo(second) >= 0 ? 1D : 0D);
	}

	private ComplexNumber le(ComplexNumber value, ComplexNumber second) {
		return new ComplexNumber(value.compareTo(second) <= 0 ? 1D : 0D);
	}

	private ComplexNumber lt(ComplexNumber value, ComplexNumber second) {
		return new ComplexNumber(value.compareTo(second) == -1 ? 1D : 0D);
	}

}
