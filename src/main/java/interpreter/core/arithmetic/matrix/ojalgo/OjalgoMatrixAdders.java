package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsAdder;
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
class OjalgoMatrixDoubleAdder extends AbstractOjalgoMatrixAdder<Double> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }

    @Override
    protected OjalgoAbstractMatrix<Double> create(MatrixStore<Double> matrixStore) {
        return new OjalgoDoubleMatrix(matrixStore);
    }

    @Override
    protected UnaryFunction<Double> getAdder(Double arg) {
        return new AbstractScalarAdder<Double>(arg) {
            @Override
            public Double invoke(Double arg) {
                return arg + value;
            }
        };
    }
}

@Component
class OjalgoMatrixComplexAdder extends AbstractOjalgoMatrixAdder<ComplexNumber> {

    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_MATRIX;
    }

    @Override
    protected OjalgoAbstractMatrix<ComplexNumber> create(MatrixStore<ComplexNumber> matrixStore) {
        return new OjalgoComplexMatrix(matrixStore);
    }

    @Override
    protected UnaryFunction<ComplexNumber> getAdder(ComplexNumber value) {
        return new AbstractScalarAdder<ComplexNumber>(value) {
            @Override
            public ComplexNumber invoke(ComplexNumber arg) {
                return arg.add(value);
            }
        };
    }
}

abstract class AbstractOjalgoMatrixAdder<T extends Number> extends AbstractOjalgoMatrixBinaryOperator<T> implements NumericObjectsAdder {
    protected abstract UnaryFunction<T> getAdder(T arg);

    @Override
    protected MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second) {
        if (first.isScalar())
            return second.getLazyStore().operateOnAll(getAdder(first.get(0)));
        else if (second.isScalar())
            return first.getLazyStore().operateOnAll(getAdder(second.get(0)));

        return first.getLazyStore().add(second.getLazyStore());
    }

    @Override
    public NumericObject add(NumericObject a, NumericObject b) {
        return operate(a, b);
    }
}

abstract class AbstractScalarAdder<N extends Number> implements UnaryFunction<N> {
    protected N value;

    public AbstractScalarAdder(N value) {
        this.value = value;
    }

    @Override
    public double invoke(double arg) {
        return arg + value.doubleValue();
    }
}