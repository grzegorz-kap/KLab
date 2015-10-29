package interpreter.service.functions.math;

import org.springframework.stereotype.Component;

import interpreter.service.functions.InternalFunction;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;

@Component
public class TanFunction extends AbstractMathFunction {

	public TanFunction() {
		super(1, 1, InternalFunction.TANGENS);
	}

	@Override
	protected ObjectData process(NumericObject[] datas) {
		return functionsHolder.get(datas[0].getNumericType(), this).tan(datas[0]);
	}

}
