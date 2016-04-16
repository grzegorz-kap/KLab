package com.klab.interpreter.types.matrix.ojalgo;

import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.MatrixBuilder;
import org.ojalgo.function.NullaryFunction;
import org.ojalgo.matrix.store.ComplexDenseStore;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class OjalgoComplexMatrixFactory extends OjalgoAbstractMatrixFactory<ComplexNumber> {

    @PostConstruct
    public void init() {
        setFactory(ComplexDenseStore.FACTORY);
        setOnesGenerator(new NullaryFunction<ComplexNumber>() {
            @Override
            public double doubleValue() {
                return 1L;
            }

            @Override
            public ComplexNumber invoke() {
                return ComplexNumber.ONE;
            }
        });
    }

    @Override
    protected Matrix<ComplexNumber> create(MatrixStore<ComplexNumber> store) {
        return new OjalgoComplexMatrix(store);
    }

    @Override
    public MatrixBuilder<ComplexNumber> builder() {
        return new OjalgoComplexMatrixBuilder();
    }

    @Override
    public NumericType supports() {
        return NumericType.COMPLEX_MATRIX;
    }
}
