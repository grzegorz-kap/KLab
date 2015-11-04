package interpreter.service.functions.math;

import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import interpreter.types.scalar.ComplexScalar;
import interpreter.types.scalar.DoubleScalar;
import org.springframework.stereotype.Component;

@Component
public class ScalarDoubleMathFunction implements MathFunctions {

	@Override
	public NumericType supports() {
		return NumericType.DOUBLE;
	}

	@Override
	public NumericObject sqrt(NumericObject value) {
		double val = getValue(value);
		if (val < 0) {
			return new ComplexScalar(0.0D, Math.sqrt(-val));
		} else {
			return new DoubleScalar(Math.sqrt(val));
		}
	}

	@Override
	public NumericObject inv(NumericObject value) throws Exception {
		return new DoubleScalar(1.0D / getValue(value));
	}

	@Override
	public NumericObject sin(NumericObject value) {
		return new DoubleScalar(Math.sin(getValue(value)));
	}

	@Override
	public NumericObject det(NumericObject value) {
		return value;
	}

	@Override
	public NumericObject tan(NumericObject value) {
		return new DoubleScalar(Math.tan(getValue(value)));
	}

	@Override
	public NumericObject cos(NumericObject value) {
		return new DoubleScalar(Math.cos(getValue(value)));
	}

	protected double getValue(NumericObject value) {
		return ((DoubleScalar) value).getValue();
	}

}
