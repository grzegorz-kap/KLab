package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsDivder;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoComplexMatrix;
import interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
import org.ojalgo.function.UnaryFunction;
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
            protected DivScalar<Double> getDivScalar(Double value) {
                return new DivScalar<Double>(value) {
                    @Override
                    public Double invoke(Double aDouble) {
                        return aDouble / value;
                    }
                };
            }

            @Override
            protected RevDivScalar<Double> getRevDivScalar(Double value) {
                return new RevDivScalar<Double>(value) {
                    @Override
                    public Double invoke(Double aDouble) {
                        return value / aDouble;
                    }
                };
            }

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
            protected DivScalar<ComplexNumber> getDivScalar(ComplexNumber value) {
                return new DivScalar<ComplexNumber>(value) {
                    @Override
                    public ComplexNumber invoke(ComplexNumber doubles) {
                        return doubles.divide(value);
                    }
                };
            }

            @Override
            protected RevDivScalar<ComplexNumber> getRevDivScalar(ComplexNumber value) {
                return new RevDivScalar<ComplexNumber>(value) {
                    @Override
                    public ComplexNumber invoke(ComplexNumber doubles) {
                        return value.divide(doubles);
                    }
                };
            }

            @Override
            protected OjalgoAbstractMatrix<ComplexNumber> create(MatrixStore<ComplexNumber> matrixStore) {
                return new OjalgoComplexMatrix(matrixStore);
            }
        };
    }

    private static abstract class OjalgoMatrixDivder<T extends Number> extends AbstractOjalgoMatrixBinaryOperator<T>
            implements NumericObjectsDivder {
        private SolverTask.Factory<T> factory;
        private NumericType supported;

        protected abstract DivScalar<T> getDivScalar(T value);

        protected abstract RevDivScalar<T> getRevDivScalar(T value);

        public OjalgoMatrixDivder(Factory<T> factory, NumericType supported) {
            this.factory = factory;
            this.supported = supported;
        }

        @Override
        protected MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second) {
            if (second.isScalar()) {
                return first.getLazyStore().operateOnAll(getDivScalar(second.get(0)));
            }
            if (first.isScalar()) {
                return second.getLazyStore().operateOnAll(getRevDivScalar(first.get(0)));
            }

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

    private static abstract class DivScalar<N extends Number> implements UnaryFunction<N> {
        protected N value;
        protected double doubleValue;

        public DivScalar(N value) {
            this.value = value;
            this.doubleValue = value.doubleValue();
        }

        @Override
        public double invoke(double v) {
            return v / doubleValue;
        }
    }

    private static abstract class RevDivScalar<N extends Number> extends DivScalar<N> {
        public RevDivScalar(N value) {
            super(value);
        }

        @Override
        public double invoke(double v) {
            return doubleValue / v;
        }
    }
}
