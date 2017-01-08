package com.klab.interpreter.core.arithmetic.matrix.ojalgo;

import com.klab.interpreter.types.NumberCreator;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.function.BinaryFunction;
import org.ojalgo.matrix.store.MatrixStore;

public class MatrixArrayConjuctionOperator<N extends Number> extends OjalgoOperator<N> {
    private NumberCreator<N> creator;

    public MatrixArrayConjuctionOperator(OjalgoMatrixCreator<N> creator, NumberCreator<N> scalarCreator) {
        super(creator);
        this.creator = scalarCreator;
    }

    @Override
    protected MatrixStore<N> operate(OjalgoAbstractMatrix<N> first, OjalgoAbstractMatrix<N> second) {
        MatrixStore<N> f = first.isScalar() ? new OjalgoMatrixScalarWrapper<>(first, second) : first.getLazyStore();
        MatrixStore<N> s = second.isScalar() ? new OjalgoMatrixScalarWrapper<>(second, first) : second.getLazyStore();
        return f.operateOnMatching(new BinaryFunction<N>() {
            @Override
            public double invoke(double arg1, double arg2) {
                return arg1 == 0.0D || arg2 == 0.0D ? 0.0D : 1.0D;
            }

            @Override
            public N invoke(N arg1, N arg2) {
                return creator.create(invoke(arg1.doubleValue(), arg2.doubleValue()));
            }
        }, s).get();
    }
}
