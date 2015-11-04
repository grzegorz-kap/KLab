package interpreter.service.functions.size;

import interpreter.service.functions.AbstractInternalFunction;
import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;
import interpreter.types.Sizeable;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SizeFunction extends AbstractInternalFunction {

    @Autowired
    private MatrixFactory<Double> matrixFactory;

    public SizeFunction() {
        super(1, 1, InternalFunction.SIZE_FUNCTION);
    }

    @Override
    public ObjectData call(ObjectData[] data) {
        Matrix<Double> matrix = matrixFactory.create(1, 2);
        Sizeable sizeable = (Sizeable) data[0];
        matrix.set(0, 0, (double) sizeable.getRows());
        matrix.set(0, 1, (double) sizeable.getColumns());
        return matrix;
    }
}
