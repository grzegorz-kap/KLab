package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.commons.ObjectData;
import interpreter.core.arithmetic.NumericObjectsMultiplicator;
import interpreter.math.matrix.ojalgo.OjalgoMatrix;
import interpreter.parsing.model.NumericType;
import org.ojalgo.matrix.store.MatrixStore;
import org.springframework.stereotype.Component;

@Component
class OjalgoMatrixDoubleMultiplicator extends AbstractOjalgoMatrixMultiplicator<Double> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
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
        return first.getMatrixStore().multiply(second.getMatrixStore());
    }
}
