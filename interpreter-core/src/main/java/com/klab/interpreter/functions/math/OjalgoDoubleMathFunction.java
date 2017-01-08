package com.klab.interpreter.functions.math;

import com.klab.interpreter.types.NumericObject;
import com.klab.interpreter.types.NumericType;
import com.klab.interpreter.types.matrix.Matrix;
import com.klab.interpreter.types.matrix.ojalgo.OjalgoDoubleMatrix;
import com.klab.interpreter.types.scalar.DoubleScalar;
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
        return new OjalgoDoubleMatrix(store);
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
