package interpreter.core.arithmetic.matrix.ojalgo;

import interpreter.types.Sizeable;
import interpreter.types.matrix.ojalgo.OjalgoAbstractMatrix;
import interpreter.types.matrix.ojalgo.OjalgoMatrixCreator;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.task.SolverTask;
import org.ojalgo.matrix.task.SolverTask.Factory;
import org.ojalgo.matrix.task.TaskException;

public class MatrixDivider<T extends Number> extends OjalgoOperator<T> {
    private SolverTask.Factory<T> factory;

    public MatrixDivider(Factory<T> factory, OjalgoMatrixCreator<T> creator) {
        super(creator);
        this.factory = factory;
    }

    @Override
    protected MatrixStore<T> operate(OjalgoAbstractMatrix<T> first, OjalgoAbstractMatrix<T> second) {
        if (second.isScalar()) {
            return first.getLazyStore()
                    .operateOnMatching(first.getDivideFunction(), new OjalgoMatrixScalarWrapper<>(second));
        }
        if (first.isScalar()) {
            return new OjalgoMatrixScalarWrapper<>(first, second)
                    .operateOnMatching(second.getDivideFunction(), second.getLazyStore());
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
    protected void checkSize(Sizeable a, Sizeable b) {
    }
}
