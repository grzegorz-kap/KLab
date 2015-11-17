package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsComparator;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
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
    protected OjalgoAbstractMatrix<Double> create(MatrixStore<Double> matrixStore) {
        return new OjalgoDoubleMatrix(matrixStore);
    }

    @Override
    protected MatrixStore<Double> operate(OjalgoAbstractMatrix<Double> first, OjalgoAbstractMatrix<Double> second) {
        return null;
    }

    @Override
    public NumericObject eq(NumericObject a, NumericObject b) {
        return operate(a, b, this::eq);
    }

    @Override
    public NumericObject neq(NumericObject a, NumericObject b) {
        return operate(a, b, this::neq);
    }

    @Override
    public NumericObject gt(NumericObject a, NumericObject b) {
        return operate(a, b, this::gt);
    }

    @Override
    public NumericObject ge(NumericObject a, NumericObject b) {
        return operate(a, b, this::ge);
    }

    @Override
    public NumericObject le(NumericObject a, NumericObject b) {
        return operate(a, b, this::le);
    }

    @Override
    public NumericObject lt(NumericObject a, NumericObject b) {
        return operate(a, b, this::lt);
    }

    private MatrixStore<Double> eq(OjalgoAbstractMatrix<Double> first, OjalgoAbstractMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::eq, second.getLazyStore());
    }

    private MatrixStore<Double> neq(OjalgoAbstractMatrix<Double> first, OjalgoAbstractMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::neq, second.getLazyStore());
    }

    private MatrixStore<Double> gt(OjalgoAbstractMatrix<Double> first, OjalgoAbstractMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::gt, second.getLazyStore());
    }

    private MatrixStore<Double> ge(OjalgoAbstractMatrix<Double> first, OjalgoAbstractMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::ge, second.getLazyStore());
    }

    private MatrixStore<Double> le(OjalgoAbstractMatrix<Double> first, OjalgoAbstractMatrix<Double> second) {
        return first.getLazyStore().operateOnMatching((PrimitiveFunction.Binary) this::le, second.getLazyStore());
    }

    private MatrixStore<Double> lt(OjalgoAbstractMatrix<Double> first, OjalgoAbstractMatrix<Double> second) {
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


