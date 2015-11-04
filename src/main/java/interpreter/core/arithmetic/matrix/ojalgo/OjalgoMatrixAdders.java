package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsAdder;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.stereotype.Component;

@Component
class OjalgoMatrixDoubleAdder extends AbstractOjalgoMatrixAdder<Double> {
    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }
}

@Component
class OjalgoMatrixComplexAdder extends AbstractOjalgoMatrixAdder<ComplexNumber> {

    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_MATRIX;
    }
}

abstract class AbstractOjalgoMatrixAdder<T extends Number> extends AbstractOjalgoMatrixBinaryOperator<T> implements NumericObjectsAdder {

    @Override
    protected MatrixStore<T> operate(OjalgoMatrix<T> first, OjalgoMatrix<T> second) {
        return first.getLazyStore().add(second.getLazyStore());
    }

    @Override
    public NumericObject add(NumericObject a, NumericObject b) {
        return operate(a, b);
    }
}
