package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.commons.exception.InterpreterCastException;
import com.klab.interpreter.types.AddressIterator;
import com.klab.interpreter.types.Addressable;
import com.klab.interpreter.types.Negable;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.scalar.ComplexScalar;
import com.klab.interpreter.types.scalar.Scalar;
import org.ojalgo.matrix.store.ComplexDenseStore;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;

import static com.klab.interpreter.commons.exception.InterpreterCastException.COMPLEX_LOGICALS;

public class OjalgoComplexMatrix extends OjalgoAbstractMatrix<ComplexNumber> implements Addressable {
    private static final Negable.UnaryNagate<ComplexNumber> NEGATE_FUN = arg -> {
        return ComplexNumber.valueOf(arg.getReal() != 0 || arg.getImaginary() != 0 ? 0.0D : 1.D);
    };

    public OjalgoComplexMatrix(MatrixStore<ComplexNumber> store) {
        super(NumericType.COMPLEX_MATRIX, OjalgoComplexMatrix::new);
        setLazyStore(store);
        setFactory(ComplexDenseStore.FACTORY);
    }

    @Override
    public Negable<ComplexNumber> negate() {
        return new OjalgoComplexMatrix(getLazyStore().operateOnAll(NEGATE_FUN).get());
    }

    @Override
    public boolean isTrue() {
        for (ComplexNumber value : getMatrixStore()) {
            if (value.getImaginary() != 0.0D) {
                throw new InterpreterCastException(COMPLEX_LOGICALS);
            }
            if (value.getReal() == 0.0D) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Scalar<ComplexNumber> createScalar(Number number) {
        return new ComplexScalar(number);
    }

    @Override
    public AddressIterator getAddressIterator() {
        return new OjalgoAddressIterator<>(getLazyStore());
    }
}
