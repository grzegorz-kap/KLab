package interpreter.service.functions.math;

import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import interpreter.types.scalar.DoubleScalar;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.PrimitiveDenseStore;
import org.ojalgo.matrix.task.DeterminantTask;
import org.ojalgo.matrix.task.InverterTask;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class OjalgoDoubleMathFunction extends OjalgoAbstractMatrixMathFunction<Double> {

    @PostConstruct
    public void init() {
        setFunctionSet(PrimitiveDenseStore.FACTORY.function());
        setDeterminantFactory(DeterminantTask.PRIMITIVE);
        setInverterFactory(InverterTask.PRIMITIVE);
    }

    @Override
    protected Matrix<Double> create(MatrixStore<Double> store) {
        return new OjalgoMatrix<>(store);
    }

    @Override
    protected NumericObject create(Double scalar) {
        return new DoubleScalar(scalar);
    }

    @Override
    public NumericType supports() {
        return NumericType.MATRIX_DOUBLE;
    }
}
