package interpreter.types.matrix.ojalgo;

import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
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
                return new ComplexNumber(1L);
            }
        });
    }

    @Override
    protected Matrix<ComplexNumber> create(MatrixStore<ComplexNumber> store) {
        return new OjalgoComplexMatrix(store);
    }

    @Override
    public NumericType supports() {
        return NumericType.COMPLEX_MATRIX;
    }
}
