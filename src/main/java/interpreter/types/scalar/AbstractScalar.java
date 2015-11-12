package interpreter.types.scalar;

import interpreter.types.*;

public abstract class AbstractScalar extends AbstractNumericObject implements Sizeable, Scalar, Indexable {

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

    @Override
    public ObjectData get(int row, int column) {
        return this;
    }

    @Override
    public ObjectData get(int cell) {
        return this;
    }
}
