package interpreter.core.arithmetic.scalar;

import org.ojalgo.function.ComplexFunction;
import org.ojalgo.scalar.ComplexNumber;

import interpreter.types.NumericObject;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.DoubleScalar;
import interpreter.types.scalar.Scalar;

public abstract class ScalarOperator<N extends Number> {
	public static final ScalarOperator<ComplexNumber> COMPLEX_ADDER = new AbstractComplexOperator() {
		@Override
		protected ComplexNumber operate(ComplexNumber a, ComplexNumber b) {
			return a.add(b);
		}
	};
	
	public static final ScalarOperator<ComplexNumber> COMPLEX_AND = new AbstractComplexOperator() {
		@Override
		protected ComplexNumber operate(ComplexNumber a, ComplexNumber b) {
			return new ComplexNumber(a.doubleValue()==0 || b.doubleValue()==0 ? 0 : 1);
		}
	};
	
	public static final ScalarOperator<ComplexNumber> COMPLEX_OR = new AbstractComplexOperator() {
		@Override
		protected ComplexNumber operate(ComplexNumber a, ComplexNumber b) {
			return new ComplexNumber(a.doubleValue()!=0 || b.doubleValue()!=0 ? 1 : 0);
		}
	};

	public static final ScalarOperator<ComplexNumber> COMPLEX_DIVIDER = new AbstractComplexOperator() {
		@Override
		protected ComplexNumber operate(ComplexNumber a, ComplexNumber b) {
			return a.divide(b);
		}
	};

	public static final ScalarOperator<ComplexNumber> COMPLEX_MULT = new AbstractComplexOperator() {
		@Override
		protected ComplexNumber operate(ComplexNumber a, ComplexNumber b) {
			return a.multiply(b);
		}
	};

	public static final ScalarOperator<ComplexNumber> COMPLEX_SUB = new AbstractComplexOperator() {
		@Override
		protected ComplexNumber operate(ComplexNumber a, ComplexNumber b) {
			return a.subtract(b);
		}
	};

	public static final ScalarOperator<ComplexNumber> COMPLEX_POW = new AbstractComplexOperator() {
		@Override
		protected ComplexNumber operate(ComplexNumber a, ComplexNumber b) {
			return ComplexFunction.POW.apply(a, b);
		}
	};

	public static final ScalarOperator<Double> DOUBLE_ADD = new AbstractDoubleOperator() {
		@Override
		protected Double operate(Double a, Double b) {
			return a + b;
		}
	};

	public static final ScalarOperator<Double> DOUBLE_SUB = new AbstractDoubleOperator() {
		@Override
		protected Double operate(Double a, Double b) {
			return a - b;
		}
	};

	public static final ScalarOperator<Double> DOUBLE_MULT = new AbstractDoubleOperator() {
		@Override
		protected Double operate(Double a, Double b) {
			return a * b;
		}
	};

	public static final ScalarOperator<Double> DOUBLE_POW = new AbstractDoubleOperator() {
		@Override
		protected Double operate(Double a, Double b) {
			return Math.pow(a, b);
		}
	};

	public static final ScalarOperator<Double> DOUBLE_DIV = new AbstractDoubleOperator() {
		@Override
		protected Double operate(Double a, Double b) {
			return a / b;
		}
	};
	
	public static final ScalarOperator<Double> DOUBLE_AND = new AbstractDoubleOperator() {
		@Override
		protected Double operate(Double a, Double b) {
			return a == 0 || b==0 ? 0D : 1D;
		}
	};
	
	public static final ScalarOperator<Double> DOUBLE_OR = new AbstractDoubleOperator() {
		@Override
		protected Double operate(Double a, Double b) {
			return a != 0 || b!=0 ? 1D : 0D;
		}
	};

	private Class<? extends Scalar<N>> clazz;
	private ScalarCreator<N> creator;

	public ScalarOperator(ScalarCreator<N> creator, Class<? extends Scalar<N>> clazz) {
		this.creator = creator;
		this.clazz = clazz;
	}

	protected abstract N operate(N a, N b);

	public Scalar<N> operate(NumericObject a, NumericObject b) {
		return creator.create(operate(clazz.cast(a).getValue(), clazz.cast(b).getValue()));
	}

	private static abstract class AbstractComplexOperator extends ScalarOperator<ComplexNumber> {
		public AbstractComplexOperator() {
			super(ComplexScalar::new, ComplexScalar.class);
		}
	}

	private static abstract class AbstractDoubleOperator extends ScalarOperator<Double> {
		public AbstractDoubleOperator() {
			super(DoubleScalar::new, DoubleScalar.class);
		}
	}
}
