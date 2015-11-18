package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsMultiplicator;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
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
}

abstract class AbstractOjalgoMatrixMultiplicator<T extends Number>
        extends AbstractOjalgoMatrixBinaryOperator<T> implements NumericObjectsMultiplicator {
    @Override
    public NumericObject mult(NumericObject a, NumericObject b) {
        return operate(a, b);
    }

    @Override
    protected MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second) {
        return first.getLazyStore().multiply(second.getLazyStore());
    }
}
