package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.commons.ObjectData;
import interpreter.core.arithmetic.NumericObjectsSubtractor;
import interpreter.parsing.model.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.springframework.stereotype.Component;

@Component
class OjalgoMatrixDoubleSubtractor extends AbstractOjalgoMatrixSubtractor<Double> {

    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }
}

abstract class AbstractOjalgoMatrixSubtractor<T extends Number>
        extends AbstractOjalgoMatrixBinaryOperator<T> implements NumericObjectsSubtractor {

    @Override
    protected MatrixStore<T> operate(OjalgoMatrix<T> first, OjalgoMatrix<T> second) {
        return first.getMatrixStore().subtract(second.getMatrixStore());
    }

    @Override
    public ObjectData sub(ObjectData a, ObjectData b) {
        return operate(a, b);
    }
}
