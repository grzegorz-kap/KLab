package interpreter.service.functions.impl;

import org.springframework.stereotype.Component;

import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;

@Component
public class RandFunction extends AbstractInternalFunction {

	public RandFunction() {
		super(1, 2, InternalFunction.RAND_FUNCTION);
	}

	@Override
	public ObjectData call(ObjectData[] datas) {
		return createMatrix(datas, matrixFactory::createRandDouble);
	}

}
