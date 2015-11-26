package interpreter.core.arithmetic.scalar;

import interpreter.types.NumericObject;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.DoubleScalar;
import interpreter.types.scalar.Scalar;
import org.ojalgo.scalar.ComplexNumber;

public abstract class ScalarOperator<N extends Number> {
    public static final ScalarOperator<ComplexNumber> COMPLEX_ADDER = new AbstractComplexOperator() {
        @Override
        protected ComplexNumber operate(ComplexNumber a, ComplexNumber b) {
            return a.add(b);
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

    public static final ScalarOperator<Double> DOUBLE_DIV = new AbstractDoubleOperator() {
        @Override
        protected Double operate(Double a, Double b) {
            return a / b;
        }
    };

    private Class<N> clazz;
    private ScalarCreator<N> creator;

    public ScalarOperator(ScalarCreator<N> creator, Class<N> clazz) {
        this.creator = creator;
        this.clazz = clazz;
    }

    protected abstract N operate(N a, N b);

    public Scalar operate(NumericObject a, NumericObject b) {
        return creator.create(operate(clazz.cast(a), clazz.cast(b)));
    }

    private static abstract class AbstractComplexOperator extends ScalarOperator<ComplexNumber> {
        public AbstractComplexOperator() {
            super(ComplexScalar::new, ComplexNumber.class);
        }
    }

    private static abstract class AbstractDoubleOperator extends ScalarOperator<Double> {
        public AbstractDoubleOperator() {
            super(DoubleScalar::new, Double.class);
        }
    }
}
