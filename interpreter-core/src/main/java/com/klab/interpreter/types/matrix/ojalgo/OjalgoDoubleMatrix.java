package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.types.AddressIterator;
import com.klab.interpreter.types.Addressable;
import com.klab.interpreter.types.Negable;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.scalar.DoubleScalar;
import com.klab.interpreter.types.scalar.Scalar;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;

public class OjalgoDoubleMatrix extends OjalgoAbstractMatrix<Double> implements Addressable {
    private static final Negable.UnaryNagate<Double> NEG_FUN = new Negable.UnaryNagate<Double>() {
        @Override
        public Double invoke(Double arg) {
            return invoke(arg.doubleValue());
        }
    };

    OjalgoDoubleMatrix() {
        super(NumericType.MATRIX_DOUBLE, OjalgoDoubleMatrix::new);

    }

    public OjalgoDoubleMatrix(MatrixStore<Double> store) {
        super(NumericType.MATRIX_DOUBLE, OjalgoDoubleMatrix::new);
        setFactory(PrimitiveDenseStore.FACTORY);
        setLazyStore(store);
    }

    @Override
    public Negable<Double> negate() {
        return new OjalgoDoubleMatrix(getLazyStore().operateOnAll(NEG_FUN).get());
    }

    @Override
    protected Scalar<Double> createScalar(Number number) {
        return new DoubleScalar(number);
    }

    @Override
    public AddressIterator getAddressIterator() {
        return new OjalgoAddressIterator<>(getLazyStore());
    }
}
