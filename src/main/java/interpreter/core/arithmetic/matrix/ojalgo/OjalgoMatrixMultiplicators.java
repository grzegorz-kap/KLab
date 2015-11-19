package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsMultiplicator;
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
class OjalgoMatrixDoubleMultiplicator extends AbstractOjalgoMatrixMultiplicator<Double> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }

    @Override
    protected OjalgoAbstractMatrix<Double> create(MatrixStore<Double> matrixStore) {
        return new OjalgoDoubleMatrix(matrixStore);
    }

    @Override
    protected MultScalar<Double> getMultScalar(Double scalar) {
        return new MultScalar<Double>(scalar) {
            @Override
            public Double invoke(Double arg) {
                return arg * value;
            }
        };
    }
}

@Component
class OjalgoMatrixComplexMultiplicator extends AbstractOjalgoMatrixMultiplicator<ComplexNumber> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_MATRIX;
    }

    @Override
    protected OjalgoAbstractMatrix<ComplexNumber> create(MatrixStore<ComplexNumber> matrixStore) {
        return new OjalgoComplexMatrix(matrixStore);
    }

    @Override
    protected MultScalar<ComplexNumber> getMultScalar(ComplexNumber scalar) {
        return new MultScalar<ComplexNumber>(scalar) {
            @Override
            public ComplexNumber invoke(ComplexNumber arg) {
                return value.multiply(arg);
            }
        };
    }
}

abstract class AbstractOjalgoMatrixMultiplicator<T extends Number> extends AbstractOjalgoMatrixBinaryOperator<T> implements NumericObjectsMultiplicator {
    protected abstract MultScalar<T> getMultScalar(T scalar);

    @Override
    public NumericObject mult(NumericObject a, NumericObject b) {
        return operate(a, b);
    }

    @Override
    protected MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second) {
        if (first.isScalar()) {
            return second.getLazyStore().operateOnAll(getMultScalar(first.get(0)));
        }
        if (second.isScalar()) {
            return first.getLazyStore().operateOnAll(getMultScalar(second.get(0)));
        }
        return first.getLazyStore().multiply(second.getLazyStore());
    }
}

abstract class MultScalar<N extends Number> implements UnaryFunction<N> {
    protected N value;

    public MultScalar(N value) {
        this.value = value;
    }

    @Override
    public double invoke(double arg) {
        return arg * value.doubleValue();
    }
}
