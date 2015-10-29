package interpreter.core.arithmetic.scalar;

import org.springframework.stereotype.Component;

import interpreter.parsing.model.NumericType;
import interpreter.types.ObjectData;
import interpreter.types.scalar.DoubleScalar;

@Component
public class ScalarDoubleNumericObjectsComparator extends AbstractComparator {

	protected ObjectData process(ObjectData a, ObjectData b, int expected) {
		Double first = ((DoubleScalar) a).getValue();
		Double second = ((DoubleScalar) b).getValue();
		return new DoubleScalar(mapToDouble(first.compareTo(second) == expected));
	}

	protected ObjectData processNot(ObjectData a, ObjectData b, int expected) {
		Double first = ((DoubleScalar) a).getValue();
		Double second = ((DoubleScalar) b).getValue();
		return new DoubleScalar(mapToDouble(first.compareTo(second) != expected));
	}

	@Override
	public NumericType getSupportedType() {
		return NumericType.DOUBLE;
	}
}
