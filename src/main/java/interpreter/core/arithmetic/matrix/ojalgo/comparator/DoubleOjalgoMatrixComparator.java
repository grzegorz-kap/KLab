package interpreter.core.arithmetic.matrix.ojalgo.comparator;

import interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.function.PrimitiveFunction;
import org.ojalgo.matrix.store.MatrixStore;

public class DoubleOjalgoMatrixComparator extends AbstractOjalgoMatrixComparator<Double> {
    public DoubleOjalgoMatrixComparator(OjalgoMatrixCreator<Double> creator) {
        super(creator);
    }

    @Override
    protected MatrixStore<Double> eq(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::eq, second);
    }

    @Override
    protected MatrixStore<Double> neq(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::neq, second);
    }

    @Override
    protected MatrixStore<Double> gt(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::gt, second);
    }

    @Override
    protected MatrixStore<Double> ge(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::ge, second);
    }

    @Override
    protected MatrixStore<Double> le(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::le, second);
    }

    @Override
    protected MatrixStore<Double> lt(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::lt, second);
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


