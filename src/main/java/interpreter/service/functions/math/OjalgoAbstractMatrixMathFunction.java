package interpreter.service.functions.math;

import interpreter.types.NumericObject;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.function.FunctionSet;
import org.ojalgo.function.UnaryFunction;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.task.DeterminantTask;
import org.ojalgo.matrix.task.InverterTask;

public abstract class OjalgoAbstractMatrixMathFunction<T extends Number> implements MathFunctions {
    protected UnaryFunction<T> sqrtFunction;
    protected UnaryFunction<T> sinFunction;
    protected UnaryFunction<T> tanFunction;
    protected UnaryFunction<T> cosFunction;
    protected InverterTask.Factory<T> inverterFactory;
    protected DeterminantTask.Factory<T> determinantFactory;

    protected abstract Matrix<T> create(MatrixStore<T> store);

    protected abstract NumericObject create(T scalar);

    @Override
    public NumericObject sqrt(NumericObject value) {
        return create(value, sqrtFunction);
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

    @Override
    public NumericObject sin(NumericObject value) {
        return create(value, sinFunction);
    }

    @Override
    public NumericObject tan(NumericObject value) {
        return create(value, tanFunction);
    }

    @Override
    public NumericObject cos(NumericObject value) {
        return create(value, cosFunction);
    }

    private MatrixStore<T> process(NumericObject numericObject) {
        return ((OjalgoMatrix<T>) numericObject).getLazyStore();
    }

    protected NumericObject create(NumericObject value, UnaryFunction<T> function) {
        return create(process(value).operateOnAll(function));
    }

    public void setFunctionSet(FunctionSet<T> functions) {
        this.sqrtFunction = functions.sqrt();
        this.sinFunction = functions.sin();
        this.cosFunction = functions.cos();
        this.tanFunction = functions.tan();
    }

    public void setInverterFactory(InverterTask.Factory<T> inverterFactory) {
        this.inverterFactory = inverterFactory;
    }

    public void setDeterminantFactory(DeterminantTask.Factory<T> determinantFactory) {
        this.determinantFactory = determinantFactory;
    }
}
