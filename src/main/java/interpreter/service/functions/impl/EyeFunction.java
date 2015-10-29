package interpreter.service.functions.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import interpreter.service.functions.IntegerArgumentsMapper;
import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;

@Component
public class EyeFunction extends AbstractInternalFunction {

	@Autowired
	private IntegerArgumentsMapper integerArgumentsMapper;

	public EyeFunction() {
		super(1, 2, InternalFunction.EYE_FUNCTION);
	}

	@Override
	public ObjectData call(ObjectData[] datas) {
		List<Integer> arguments = integerArgumentsMapper.mapToIntArgs(datas, this);
		int rows = arguments.get(0);
		int cols = arguments.size() == 2 ? arguments.get(1) : rows;
		return matrixFactory.createEyeDouble(rows, cols);
	}

}
