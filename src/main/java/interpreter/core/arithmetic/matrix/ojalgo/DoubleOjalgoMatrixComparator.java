package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsComparator;
import interpreter.types.NumericType;
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

    @Override
    public ObjectData neq(ObjectData a, ObjectData b) {
        return operate(a, b, this::neq);
    }

    @Override
    public ObjectData gt(ObjectData a, ObjectData b) {
        return operate(a, b, this::gt);
    }

    @Override
    public ObjectData ge(ObjectData a, ObjectData b) {
        return operate(a, b, this::ge);
    }

    @Override
    public ObjectData le(ObjectData a, ObjectData b) {
        return operate(a, b, this::le);
    }

    @Override
    public ObjectData lt(ObjectData a, ObjectData b) {
        return operate(a, b, this::lt);
    }

    private MatrixStore<Double> eq(OjalgoMatrix<Double> first, OjalgoMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::eq, second.getLazyStore());
    }

    private MatrixStore<Double> neq(OjalgoMatrix<Double> first, OjalgoMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::neq, second.getLazyStore());
    }

    private MatrixStore<Double> gt(OjalgoMatrix<Double> first, OjalgoMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::gt, second.getLazyStore());
    }

    private MatrixStore<Double> ge(OjalgoMatrix<Double> first, OjalgoMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::ge, second.getLazyStore());
    }

    private MatrixStore<Double> le(OjalgoMatrix<Double> first, OjalgoMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::le, second.getLazyStore());
    }

    private MatrixStore<Double> lt(OjalgoMatrix<Double> first, OjalgoMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::lt, second.getLazyStore());
    }

    private Double eq(Double value, Double second) {
        return value.equals(second) ? 1.0D : 0.0D;
    }

    private Double neq(Double value, Double second) {
        return value.equals(second) ? 0.0D : 1.0D;
    }

    private Double gt(Double value, Double second) {
        return value.compareTo(second) == 1 ? 1D : 0D;
    }

    private Double ge(Double value, Double second) {
        return value.compareTo(second) >= 0 ? 1D : 0D;
    }

    private Double le(Double value, Double second) {
        return value.compareTo(second) <= 0 ? 1D : 0D;
    }

    private Double lt(Double value, Double second) {
        return value.compareTo(second) == -1 ? 1D : 0D;
    }
}


