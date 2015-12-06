package interpreter.core.arithmetic.matrix.ojalgo.comparator;

import interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.function.ComplexFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;

public class CompexOjalgoMatrixComparator extends AbstractOjalgoMatrixComparator<ComplexNumber> {
    public CompexOjalgoMatrixComparator(OjalgoMatrixCreator<ComplexNumber> creator) {
        super(creator);
    }

    @Override
    protected MatrixStore<ComplexNumber> eq(MatrixStore<ComplexNumber> first, MatrixStore<ComplexNumber> second) {
        return first.operateOnMatching((ComplexFunction.Binary) this::eq, second);
    }

    @Override
    protected MatrixStore<ComplexNumber> neq(MatrixStore<ComplexNumber> first, MatrixStore<ComplexNumber> second) {
        return first.operateOnMatching((ComplexFunction.Binary) this::neq, second);
    }

    @Override
    protected MatrixStore<ComplexNumber> gt(MatrixStore<ComplexNumber> first, MatrixStore<ComplexNumber> second) {
        return first.operateOnMatching((ComplexFunction.Binary) this::gt, second);
    }

    @Override
    protected MatrixStore<ComplexNumber> ge(MatrixStore<ComplexNumber> first, MatrixStore<ComplexNumber> second) {
        return first.operateOnMatching((ComplexFunction.Binary) this::ge, second);
    }

    @Override
    protected MatrixStore<ComplexNumber> le(MatrixStore<ComplexNumber> first, MatrixStore<ComplexNumber> second) {
        return first.operateOnMatching((ComplexFunction.Binary) this::le, second);
    }

    @Override
    protected MatrixStore<ComplexNumber> lt(MatrixStore<ComplexNumber> first, MatrixStore<ComplexNumber> second) {
        return first.operateOnMatching((ComplexFunction.Binary) this::lt, second);
    }

    private ComplexNumber eq(ComplexNumber value, ComplexNumber second) {
        return new ComplexNumber(value.equals(second) ? 1.0D : 0.0D);
    }

    private ComplexNumber neq(ComplexNumber value, ComplexNumber second) {
        return new ComplexNumber(value.equals(second) ? 0.0D : 1.0D);
    }

    private ComplexNumber gt(ComplexNumber value, ComplexNumber second) {
        return new ComplexNumber(value.compareTo(second) == 1 ? 1D : 0D);
    }

    private ComplexNumber ge(ComplexNumber value, ComplexNumber second) {
        return new ComplexNumber(value.compareTo(second) >= 0 ? 1D : 0D);
    }

    private ComplexNumber le(ComplexNumber value, ComplexNumber second) {
        return new ComplexNumber(value.compareTo(second) <= 0 ? 1D : 0D);
    }

    private ComplexNumber lt(ComplexNumber value, ComplexNumber second) {
        return new ComplexNumber(value.compareTo(second) == -1 ? 1D : 0D);
    }
}
