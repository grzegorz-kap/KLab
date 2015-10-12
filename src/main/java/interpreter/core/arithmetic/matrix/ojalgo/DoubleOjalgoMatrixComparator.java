package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsComparator;
import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.function.PrimitiveFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.springframework.stereotype.Component;

@Component
public class DoubleOjalgoMatrixComparator
        extends AbstractOjalgoMatrixBinaryOperator<Double> implements NumericObjectsComparator {

    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }

    @Override
    protected MatrixStore<Double> operate(OjalgoMatrix<Double> first, OjalgoMatrix<Double> second) {
        return null;
    }

    @Override
    public ObjectData eq(ObjectData a, ObjectData b) {
        return operate(a, b, this::eq);
    }

    private MatrixStore<Double> eq(OjalgoMatrix<Double> first, OjalgoMatrix<Double> second) {
        return first.getMatrixStore().operateOnMatching((PrimitiveFunction.Binary) this::eq, second.getMatrixStore());
    }


    private Double eq(Double value, Double second) {
        return value.equals(second) ? 1.0D : 0.0D;
    }
}


