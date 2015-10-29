package interpreter.service.functions.math;

import org.springframework.stereotype.Component;

import interpreter.commons.exception.IllegalArgumentException;
import interpreter.service.functions.InternalFunction;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;

@Component
public class SqrtInternalFunction extends AbstractMathFunction {

	public SqrtInternalFunction() {
		super(1, 1, InternalFunction.SQRT_FUNCTION);
	}

	@Override
	public ObjectData call(ObjectData[] datas) {
		if (datas[0] instanceof NumericObject) {
			NumericObject numericObject = (NumericObject) datas[0];
			return functionsHolder.get(numericObject.getNumericType(), this).sqrt(numericObject);
		} else {
			throw new IllegalArgumentException("Numeric object expected");
		}
	}

}
