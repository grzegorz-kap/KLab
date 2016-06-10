package com.klab.interpreter.types.scalar;

import com.klab.interpreter.commons.exception.InterpreterCastException;
import com.klab.interpreter.types.*;

abstract class AbstractScalar<N extends Number> extends AbstractNumericObject implements Sizeable, Scalar<N>, Indexable {
    static int getIntOrThrow(double value) {
        if (Math.rint(value) == value) {
            return (int) value;
        } else {
            throw new InterpreterCastException(InterpreterCastException.EXPECTED_INTEGER_VALUE);
        }
    }

    private boolean temp;

    AbstractScalar(NumericType numericType) {
        super(numericType);
    }

    @Override
    public boolean isTemp() {
        return temp;
    }

    @Override
    public void setTemp(boolean temp) {
        this.temp = temp;
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
    public long getCells() {
        return 1;
    }

    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public int getColumns() {
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
