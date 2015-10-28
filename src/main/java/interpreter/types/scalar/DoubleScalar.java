package interpreter.types.scalar;

import java.util.Objects;

import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;

public class DoubleScalar extends AbstractScalar {

	private Double value;

	public DoubleScalar() {
		super(NumericType.DOUBLE);
	}

	public DoubleScalar(Double value) {
		this();
		this.value = value;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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
}
