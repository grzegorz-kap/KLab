package interpreter.service.functions.math;

import org.springframework.stereotype.Component;

import interpreter.service.functions.InternalFunction;
import interpreter.types.NumericObject;
import interpreter.types.ObjectData;

@Component
public class CosinusFunction extends AbstractMathFunction {

	public CosinusFunction() {
		super(1, 1, InternalFunction.COSINUS);
	}

	@Override
	protected ObjectData process(NumericObject[] datas) {
		return functionsHolder.get(datas[0].getNumericType(), this).cos(datas[0]);
	}

}
