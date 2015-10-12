package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.core.arithmetic.NumericObjectsDivider;
import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.matrix.ojalgo.OjalgoMatrix;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.task.SolverTask;
import org.ojalgo.matrix.task.TaskException;
import org.springframework.stereotype.Component;

@Component
class OjalgoMatrixDoubleDivider extends AbstractOjalgoMatrixBinaryOperator<Double> implements NumericObjectsDivider {

    @Override
    public NumericType getSupportedType() {
        return NumericType.MATRIX_DOUBLE;
    }

    @Override
    protected MatrixStore<Double> operate(OjalgoMatrix<Double> first, OjalgoMatrix<Double> second) {
        try {
            MatrixStore<Double> aT = first.getMatrixStore().transpose();
            MatrixStore<Double> bT = second.getMatrixStore().transpose();
            return SolverTask.PRIMITIVE.make(aT, bT).solve(aT, bT).transpose();
        } catch (TaskException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectData div(ObjectData a, ObjectData b) {
        return operate(a, b);
    }
}
