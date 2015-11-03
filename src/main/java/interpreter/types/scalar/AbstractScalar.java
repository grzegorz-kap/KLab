package interpreter.types.scalar;

import interpreter.types.AbstractNumericObject;
import interpreter.types.NumericType;
import interpreter.types.Sizeable;

public abstract class AbstractScalar extends AbstractNumericObject implements Sizeable, Scalar {

    public AbstractScalar(NumericType numericType) {
        super(numericType);
    }

    @Override
    public long getRows() {
        return 1;
    }

    @Override
    public long getColumns() {
        return 1;
    }
}
