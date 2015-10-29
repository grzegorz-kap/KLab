package interpreter.service.functions.math;

import org.springframework.stereotype.Component;

import interpreter.parsing.model.NumericType;
import interpreter.types.NumericObject;
import interpreter.types.scalar.DoubleScalar;

@Component
public class ScalarDoubleMathFunction implements MathFunctions {

	@Override
	public NumericType supports() {
		return NumericType.DOUBLE;
	}

	@Override
	public NumericObject sqrt(NumericObject value) {
		DoubleScalar scalar = (DoubleScalar) value;
		return new DoubleScalar(Math.sqrt(scalar.getValue()));
	}

	@Override
	public NumericObject inv(NumericObject value) throws Exception {
		DoubleScalar scalar = (DoubleScalar) value;
		return new DoubleScalar(1.0D/scalar.getValue());
	}

	@Override
	public NumericObject sin(NumericObject value) {
		DoubleScalar scalar = (DoubleScalar) value;
		return new DoubleScalar(Math.sin(scalar.getValue()));
	}

}
