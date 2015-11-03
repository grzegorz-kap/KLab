package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsSubtractor;
import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.stereotype.Component;

@Component
class OjalgoMatrixDoubleSubtractor extends AbstractOjalgoMatrixSubtractor<Double> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }
}

@Component
class OjalgoMatrixComplexSubtractor extends AbstractOjalgoMatrixSubtractor<ComplexNumber> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_MATRIX;
    }
}

abstract class AbstractOjalgoMatrixSubtractor<T extends Number> extends AbstractOjalgoMatrixBinaryOperator<T>
        implements NumericObjectsSubtractor {
    @Override
    protected MatrixStore<T> operate(OjalgoMatrix<T> first, OjalgoMatrix<T> second) {
        return first.getLazyStore().subtract(second.getLazyStore());
    }

    @Override
    public ObjectData sub(ObjectData a, ObjectData b) {
        return operate(a, b);
    }
}
