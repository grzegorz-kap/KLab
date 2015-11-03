package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsMultiplicator;
import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.stereotype.Component;

@Component
class OjalgoMatrixDoubleMultiplicator extends AbstractOjalgoMatrixMultiplicator<Double> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }
}

@Component
class OjalgoMatrixComplexMultiplicator extends AbstractOjalgoMatrixMultiplicator<ComplexNumber> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_MATRIX;
    }
}

abstract class AbstractOjalgoMatrixMultiplicator<T extends Number>
        extends AbstractOjalgoMatrixBinaryOperator<T> implements NumericObjectsMultiplicator {
    @Override
    public ObjectData mult(ObjectData a, ObjectData b) {
        return operate(a, b);
    }

    @Override
    protected MatrixStore<T> operate(OjalgoMatrix<T> first, OjalgoMatrix<T> second) {
        return first.getLazyStore().multiply(second.getLazyStore());
    }
}
