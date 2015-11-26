package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsAdder;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
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
}

abstract class AbstractOjalgoMatrixAdder<T extends Number> extends AbstractOjalgoMatrixBinaryOperator<T> implements NumericObjectsAdder {
    @Override
    protected MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second) {
        if (first.isScalar())
            return second.getLazyStore().add(new OjalgoMatrixScalarWrapper<>(first));
        else if (second.isScalar())
            return first.getLazyStore().add(new OjalgoMatrixScalarWrapper<>(second));

        return first.getLazyStore().add(second.getLazyStore());
    }

    @Override
    public NumericObject add(NumericObject a, NumericObject b) {
        return operate(a, b);
    }
}