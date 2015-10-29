package interpreter.core.arithmetic.scalar;

import org.springframework.stereotype.Component;

import interpreter.core.arithmetic.NumericObjectsAdder;
import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.ComplexScalar;

@Component
public class ComplexScalarDoubleNumericObjectsAdder implements NumericObjectsAdder {

	@Override
	public NumericType getSupportedType() {
		return NumericType.COMPLEX_DOUBLE;
	}

	@Override
	public ObjectData add(ObjectData a, ObjectData b) {
		ComplexScalar first = (ComplexScalar) a;
		ComplexScalar second = (ComplexScalar) b;
		return new ComplexScalar(first.getComplex().add(second.getComplex()));
	}

}
