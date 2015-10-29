package interpreter.service.functions.impl;

import org.springframework.stereotype.Component;

import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;

@Component
public class OnesFunction extends AbstractInternalFunction {

	public OnesFunction() {
		super(1, 2, InternalFunction.ONES_FUNCTION);
	}

	@Override
	public ObjectData call(ObjectData[] datas) {
		return createMatrix(datas, matrixFactory::createOnesDouble);
	}

}
