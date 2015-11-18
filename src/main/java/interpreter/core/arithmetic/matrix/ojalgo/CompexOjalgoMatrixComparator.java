package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsComparator;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import org.ojalgo.function.ComplexFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.stereotype.Component;

@Component
public class CompexOjalgoMatrixComparator extends AbstractOjalgoMatrixBinaryOperator<ComplexNumber> implements NumericObjectsComparator {
    @Override
    public NumericType getSupportedType() {
        return NumericType.COMPLEX_MATRIX;
    }

    @Override
    protected OjalgoAbstractMatrix<ComplexNumber> create(MatrixStore<ComplexNumber> matrixStore) {
        return new OjalgoComplexMatrix(matrixStore);
    }

    @Override
    protected MatrixStore<ComplexNumber> operate(OjalgoAbstractMatrix<ComplexNumber> first, OjalgoAbstractMatrix<ComplexNumber> second) {
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

    private MatrixStore<ComplexNumber> eq(OjalgoAbstractMatrix<ComplexNumber> first, OjalgoAbstractMatrix<ComplexNumber> second) {
        return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::eq, second.getLazyStore());
    }

    private MatrixStore<ComplexNumber> neq(OjalgoAbstractMatrix<ComplexNumber> first, OjalgoAbstractMatrix<ComplexNumber> second) {
        return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::neq, second.getLazyStore());
    }

    private MatrixStore<ComplexNumber> gt(OjalgoAbstractMatrix<ComplexNumber> first, OjalgoAbstractMatrix<ComplexNumber> second) {
        return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::gt, second.getLazyStore());
    }

    private MatrixStore<ComplexNumber> ge(OjalgoAbstractMatrix<ComplexNumber> first, OjalgoAbstractMatrix<ComplexNumber> second) {
        return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::ge, second.getLazyStore());
    }

    private MatrixStore<ComplexNumber> le(OjalgoAbstractMatrix<ComplexNumber> first, OjalgoAbstractMatrix<ComplexNumber> second) {
        return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::le, second.getLazyStore());
    }

    private MatrixStore<ComplexNumber> lt(OjalgoAbstractMatrix<ComplexNumber> first, OjalgoAbstractMatrix<ComplexNumber> second) {
        return first.getLazyStore().operateOnMatching((ComplexFunction.Binary) this::lt, second.getLazyStore());
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
