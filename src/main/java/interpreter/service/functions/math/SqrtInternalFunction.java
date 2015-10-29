package interpreter.service.functions.math;

import org.springframework.stereotype.Component;

import interpreter.service.functions.InternalFunction;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;

@Component
public class SqrtInternalFunction extends AbstractMathFunction {

	public SqrtInternalFunction() {
		super(1, 1, InternalFunction.SQRT_FUNCTION);
	}

	@Override
	public ObjectData process(NumericObject[] datas) {
		return functionsHolder.get(datas[0].getNumericType(), this).sqrt(datas[0]);
	}

}
