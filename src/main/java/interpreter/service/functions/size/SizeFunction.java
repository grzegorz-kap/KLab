package interpreter.service.functions.size;

import interpreter.service.functions.AbstractInternalFunction;
import interpreter.service.functions.InternalFunction;
import interpreter.types.MultiOutput;
import interpreter.types.ObjectData;
import interpreter.types.Sizeable;
import interpreter.types.matrix.Matrix;
import interpreter.types.matrix.MatrixFactory;
import interpreter.types.scalar.NumberScalarFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SizeFunction extends AbstractInternalFunction {
    @Autowired
    private MatrixFactory<Double> matrixFactory;

    @Autowired
    private NumberScalarFactory numberScalarFactory;

    public SizeFunction() {
        super(1, 1, InternalFunction.SIZE_FUNCTION);
    }

    @Override
    public ObjectData call(ObjectData[] data, int output) {
        // TODO optimize
        Sizeable sizeable = (Sizeable) data[0];
        long rows = sizeable.getRows();
        long columns = sizeable.getColumns();
        if (output == -1) {
            Matrix<Double> matrix = matrixFactory.create(1, 2);
            matrix.set(0, 0, (double) rows);
            matrix.set(0, 1, (double) columns);
            return matrix;
        } else {
            return MultiOutput.build()
                    .add(numberScalarFactory.getDouble(rows))
                    .add(numberScalarFactory.getDouble(columns));
        }
    }
}
