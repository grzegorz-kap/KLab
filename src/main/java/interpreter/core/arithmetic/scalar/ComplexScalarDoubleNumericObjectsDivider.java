package interpreter.core.arithmetic.scalar;

import org.springframework.stereotype.Component;

import interpreter.core.arithmetic.NumericObjectsDivder;
import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.ComplexScalar;

@Component
public class ComplexScalarDoubleNumericObjectsDivider implements NumericObjectsDivder {
	
	@Override
	public NumericType getSupportedType() {
		return NumericType.COMPLEX_DOUBLE;
	}

	@Override
	public ObjectData div(ObjectData a, ObjectData b) {
		ComplexScalar first = (ComplexScalar) a;
		ComplexScalar second = (ComplexScalar) b;
		return new ComplexScalar(first.getComplex().divide(second.getComplex()));
	}
}
