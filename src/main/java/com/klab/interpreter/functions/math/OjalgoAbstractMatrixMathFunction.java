package com.klab.interpreter.functions.math;

import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import org.ojalgo.function.BinaryFunction;
import org.ojalgo.function.FunctionSet;
import org.ojalgo.function.UnaryFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.task.DeterminantTask;
import org.ojalgo.matrix.task.InverterTask;

public abstract class OjalgoAbstractMatrixMathFunction<T extends Number> implements MathFunctions {
    private InverterTask.Factory<T> inverterFactory;
    private DeterminantTask.Factory<T> determinantFactory;
    private FunctionSet<T> functions;

    protected abstract Matrix<T> create(MatrixStore<T> store);

    protected abstract NumericObject create(T scalar);

    protected NumericObject create(NumericObject value, UnaryFunction<T> function) {
        return create(process(value).operateOnAll(function).get());
    }

    @Override
    public NumericObject log(NumericObject a) {
        return create(a, functions.log());
    }

    @Override
    public NumericObject log10(NumericObject a) {
        return create(a, functions.log10());
    }

    @Override
    public NumericObject log(NumericObject a, NumericObject b) {
        final UnaryFunction<T> log10 = functions.log10();
        final BinaryFunction<T> divide = functions.divide();
        final T base = log10.apply((T) ((Matrix<?>) a).getValue());
        final double baseDouble = base.doubleValue();

        return create(b, new UnaryFunction<T>() {
            @Override
            public double invoke(double arg) {
                return log10.applyAsDouble(arg) / baseDouble;
            }

            @Override
            public T invoke(T arg) {
                return divide.apply(log10.apply(arg), base);
            }
        });
    }

    @Override
    public NumericObject sqrt(NumericObject value) {
        return create(value, functions.sqrt());
    }

    @Override
    public NumericObject sin(NumericObject value) {
        return create(value, functions.sin());
    }

    @Override
    public NumericObject cos(NumericObject value) {
        return create(value, functions.cos());
    }

    @Override
    public NumericObject tan(NumericObject value) {
        return create(value, functions.tan());
    }

    @Override
    public NumericObject inv(NumericObject value) throws Exception {
        MatrixStore<T> matrixStore = process(value);
        return create(inverterFactory.make(matrixStore).invert(matrixStore));
    }

    @Override
    public NumericObject det(NumericObject value) {
        MatrixStore<T> matrixStore = process(value);
        return create(determinantFactory.make(matrixStore).calculateDeterminant(matrixStore));
    }

    private MatrixStore<T> process(NumericObject numericObject) {
        return ((OjalgoAbstractMatrix<T>) numericObject).getLazyStore();
    }

    void setFunctionSet(FunctionSet<T> functions) {
        this.functions = functions;
    }

    void setInverterFactory(InverterTask.Factory<T> inverterFactory) {
        this.inverterFactory = inverterFactory;
    }

    void setDeterminantFactory(DeterminantTask.Factory<T> determinantFactory) {
        this.determinantFactory = determinantFactory;
    }
}
