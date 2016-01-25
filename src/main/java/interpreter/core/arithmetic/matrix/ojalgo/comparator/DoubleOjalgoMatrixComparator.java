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
        return first.operateOnMatching((PrimitiveFunction.Binary) this::eq, second).get();
    }

    @Override
    protected MatrixStore<Double> neq(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::neq, second).get();
    }

    @Override
    protected MatrixStore<Double> gt(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::gt, second).get();
    }

    @Override
    protected MatrixStore<Double> ge(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::ge, second).get();
    }

    @Override
    protected MatrixStore<Double> le(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::le, second).get();
    }

    @Override
    protected MatrixStore<Double> lt(MatrixStore<Double> first, MatrixStore<Double> second) {
        return first.operateOnMatching((PrimitiveFunction.Binary) this::lt, second).get();
    }

    private double eq(double value, double second) {
        return value == second ? 1.0D : 0.0D;
    }

    private double neq(double value, double second) {
        return value == second ? 0.0D : 1.0D;
    }

    private double gt(double value, double second) {
        return value > second ? 1D : 0D;
    }

    private double ge(double value, double second) {
        return value >= second ? 1D : 0D;
    }

    private double le(double value, double second) {
        return value <= second ? 1D : 0D;
    }

    private double lt(double value, double second) {
        return value < second ? 1D : 0D;
    }
}


