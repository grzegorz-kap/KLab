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

}
