package interpreter.types.scalar;

import java.util.Objects;

import interpreter.types.AddressIterator;
import interpreter.types.Addressable;
import interpreter.types.NumericType;
import interpreter.types.ObjectData;

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
}
