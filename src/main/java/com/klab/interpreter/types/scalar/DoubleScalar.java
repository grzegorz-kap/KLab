package com.klab.interpreter.types.scalar;

import com.klab.interpreter.types.*;

import java.util.Objects;

public class DoubleScalar extends AbstractScalar<Double> implements Addressable {
    private Double value;

    public DoubleScalar() {
        super(NumericType.DOUBLE);
    }

    public DoubleScalar(Double value) {
        this();
        this.value = value;
    }

    public DoubleScalar(Number number) {
        this();
        this.value = number.doubleValue();
    }

    @Override
    public Editable<Double> edit(AddressIterator row, AddressIterator column, EditSupplier<Double> supplier) {
        return edit(null, supplier);
    }

    @Override
    public Editable<Double> edit(AddressIterator cells, EditSupplier<Double> supplier) {
        value = supplier.next();
        return this;
    }

    @Override
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public int getIntOrThrow() {
        return AbstractScalar.getIntOrThrow(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public ObjectData copyObjectData() {
        return new DoubleScalar(value);
    }

    @Override
    public boolean isTrue() {
        return Objects.nonNull(value) && !value.equals(0.0D);
    }

    @Override
    public AddressIterator getAddressIterator() {
        return new AddressScalarIterator(getIntOrThrow());
    }

    @Override
    public Negable<Scalar<Double>> negate() {
        return new DoubleScalar(value == 0.0D ? 1.0D : 0.0D);
    }
}
