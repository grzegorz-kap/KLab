package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsSubtractor;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
import org.ojalgo.function.UnaryFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.stereotype.Component;

@Component
class OjalgoMatrixDoubleSubtractor extends AbstractOjalgoMatrixSubtractor<Double> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }

    @Override
    protected OjalgoAbstractMatrix<Double> create(MatrixStore<Double> matrixStore) {
        return new OjalgoDoubleMatrix(matrixStore);
    }

    @Override
    protected UnaryFunction<Double> getSubScalar(Double arg) {
        return new SubScalar<Double>(arg) {
            @Override
            public Double invoke(Double arg) {
                return arg - value;
            }
        };
    }

    @Override
    protected ReverseSubScalar<Double> getRevSubScalar(Double arg) {
        return new ReverseSubScalar<Double>(arg) {
            @Override
            public Double invoke(Double arg) {
                return value - arg;
            }
        };
    }
}

@Component
class OjalgoMatrixComplexSubtractor extends AbstractOjalgoMatrixSubtractor<ComplexNumber> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_MATRIX;
    }

    @Override
    protected OjalgoAbstractMatrix<ComplexNumber> create(MatrixStore<ComplexNumber> matrixStore) {
        return new OjalgoComplexMatrix(matrixStore);
    }

    @Override
    protected UnaryFunction<ComplexNumber> getSubScalar(ComplexNumber arg) {
        return new SubScalar<ComplexNumber>(arg) {
            @Override
            public ComplexNumber invoke(ComplexNumber arg) {
                return arg.subtract(value);
            }
        };
    }

    @Override
    protected ReverseSubScalar<ComplexNumber> getRevSubScalar(ComplexNumber arg) {
        return new ReverseSubScalar<ComplexNumber>(arg) {
            @Override
            public ComplexNumber invoke(ComplexNumber arg) {
                return value.subtract(arg);
            }
        };
    }
}

abstract class AbstractOjalgoMatrixSubtractor<N extends Number> extends AbstractOjalgoMatrixBinaryOperator<N> implements NumericObjectsSubtractor {
    protected abstract UnaryFunction<N> getSubScalar(N arg);

    protected abstract UnaryFunction<N> getRevSubScalar(N arg);

    @Override
    protected MatrixStore<N> operate(OjalgoAbstractMatrix<N> first, OjalgoAbstractMatrix<N> second) {
        if (second.isScalar()) {
            return first.getLazyStore().operateOnAll(getSubScalar(second.get(0)));
        }
        if (first.isScalar()) {
            return second.getLazyStore().operateOnAll(getRevSubScalar(first.get(0)));
        }
        return first.getLazyStore().subtract(second.getLazyStore());
    }

    @Override
    public NumericObject sub(NumericObject a, NumericObject b) {
        return operate(a, b);
    }
}

abstract class SubScalar<N extends Number> implements UnaryFunction<N> {
    protected N value;

    public SubScalar(N value) {
        this.value = value;
    }

    @Override
    public double invoke(double arg) {
        return arg - value.doubleValue();
    }
}

abstract class ReverseSubScalar<N extends Number> extends SubScalar<N> {
    public ReverseSubScalar(N value) {
        super(value);
    }

    @Override
    public double invoke(double arg) {
        return value.doubleValue() - arg;
    }
}
