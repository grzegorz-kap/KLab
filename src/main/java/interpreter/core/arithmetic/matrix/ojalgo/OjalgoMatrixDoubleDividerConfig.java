package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsDivder;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.task.SolverTask;
import org.ojalgo.matrix.task.SolverTask.Factory;
import org.ojalgo.matrix.task.TaskException;
import org.ojalgo.scalar.ComplexNumber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OjalgoMatrixDoubleDividerConfig {

    @Bean
    public OjalgoMatrixDivder<Double> ojalgoMatrixDoubleDivider() {
        return new OjalgoMatrixDivder<Double>(SolverTask.PRIMITIVE, NumericType.MATRIX_DOUBLE) {
            @Override
            protected OjalgoAbstractMatrix<Double> create(MatrixStore<Double> matrixStore) {
                return new OjalgoDoubleMatrix(matrixStore);
            }
        };
    }

    @Bean
    public OjalgoMatrixDivder<ComplexNumber> ojalgoMatrixComplexDivider() {
        return new OjalgoMatrixDivder<ComplexNumber>(SolverTask.COMPLEX, NumericType.COMPLEX_MATRIX) {
            @Override
            protected OjalgoAbstractMatrix<ComplexNumber> create(MatrixStore<ComplexNumber> matrixStore) {
                return new OjalgoComplexMatrix(matrixStore);
            }
        };
    }

    private static abstract class OjalgoMatrixDivder<T extends Number> extends AbstractOjalgoMatrixBinaryOperator<T> implements NumericObjectsDivder {

        private SolverTask.Factory<T> factory;
        private NumericType supported;

        public OjalgoMatrixDivder(Factory<T> factory, NumericType supported) {
            this.factory = factory;
            this.supported = supported;
        }

        @Override
        protected MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second) {
            try {
                MatrixStore<T> aT = first.getLazyStore().transpose();
                MatrixStore<T> bT = second.getLazyStore().transpose();
                return factory.make(aT, bT).solve(aT, bT).transpose();
            } catch (TaskException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public NumericObject div(NumericObject a, NumericObject b) {
            return operate(a, b);
        }

        @Override
        public NumericType getSupportedType() {
            return supported;
        }
    }
}
