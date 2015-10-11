package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.commons.ObjectData;
import interpreter.core.arithmetic.NumericObjectsAdder;
import interpreter.parsing.model.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.springframework.stereotype.Component;

@Component
class OjalgoMatrixDoubleAdder extends AbstractOjalgoMatrixAdder<Double> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }
}

abstract class AbstractOjalgoMatrixAdder<T extends Number> extends AbstractOjalgoMatrixBinaryOperator<T> implements NumericObjectsAdder {

    @Override
    protected MatrixStore<T> operate(OjalgoMatrix<T> first, OjalgoMatrix<T> second) {
        return first.getMatrixStore().add(second.getMatrixStore());
    }

    @Override
    public ObjectData add(ObjectData a, ObjectData b) {
        return operate(a, b);
    }
}
