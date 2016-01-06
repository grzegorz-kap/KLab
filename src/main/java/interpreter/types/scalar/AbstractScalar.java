package interpreter.types.scalar;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.types.*;

public abstract class AbstractScalar<N extends Number> extends AbstractNumericObject implements Sizeable, Scalar<N>, Indexable {
    public AbstractScalar(NumericType numericType) {
        super(numericType);
    }

    public static int getIntOrThrow(double value) {
        if (Math.rint(value) == value) {
            return (int) value;
        } else {
            throw new InterpreterCastException(InterpreterCastException.EXPECTED_INTEGER_VALUE);
        }
    }

    @Override
    public EditSupplier<N> getEditSupplier() {
        return new EditSupplier<N>() {
            public boolean hasNext() {
                return true;
            }
            public N next() {
                return getValue();
            }
        };
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
    public ObjectData get(AddressIterator row, AddressIterator column) {
        return this;
    }

    @Override
    public ObjectData get(AddressIterator cell) {
        return this;
    }
}
