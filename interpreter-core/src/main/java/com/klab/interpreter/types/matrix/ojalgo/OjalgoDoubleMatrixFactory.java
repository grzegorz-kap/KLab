package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.MatrixBuilder;
import org.ojalgo.function.NullaryFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class OjalgoDoubleMatrixFactory extends OjalgoAbstractMatrixFactory<Double> {
    @PostConstruct
    public void init() {
        setFactory(PrimitiveDenseStore.FACTORY);
        setOnesGenerator(new NullaryFunction<Double>() {
            @Override
            public double doubleValue() {
                return 1D;
            }

            @Override
            public Double invoke() {
                return 1D;
            }
        });
    }

    @Override
    protected Matrix<Double> create(MatrixStore<Double> store) {
        return new OjalgoDoubleMatrix(store);
    }

    @Override
    public MatrixBuilder<Double> builder() {
        return new OjalgoDoubleMatrixBuilder();
    }

    @Override
    public NumericType supports() {
        return NumericType.MATRIX_DOUBLE;
    }
}
