package interpreter.core.arithmetic.scalar;

import org.ojalgo.scalar.ComplexNumber;

import interpreter.types.NumericObject;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.DoubleScalar;
import interpreter.types.scalar.Scalar;

public abstract class ScalarNegator<N extends Number> {
	public static final ScalarNegator<Double> DOUBLE_NEGATOR = new ScalarNegator<Double>(DoubleScalar.class) {
		@Override
		protected Scalar<Double> operatate(Scalar<Double> scalar) {
			return new DoubleScalar(-scalar.getValue());
		}
	};
	
	public static final ScalarNegator<ComplexNumber> COMPLEX_NEGATOR = new ScalarNegator<ComplexNumber>(ComplexScalar.class) {
		@Override
		protected Scalar<ComplexNumber> operatate(Scalar<ComplexNumber> scalar) {
			return new ComplexScalar(scalar.getValue().negate());
		}
	};
	
	private Class<? extends Scalar<N>> clazz;
	
	public ScalarNegator(Class<? extends Scalar<N>> clazz) {
		this.clazz = clazz;
	}
	
	public Scalar<N> operate(NumericObject a) {
		return operatate(clazz.cast(a));
	}
	
	protected abstract Scalar<N> operatate(Scalar<N> scalar);
}
