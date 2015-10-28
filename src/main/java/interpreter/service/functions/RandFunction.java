package interpreter.service.functions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import interpreter.types.ObjectData;

@Component
public class RandFunction extends AbstractInternalFunction {

	@Autowired
	private IntegerArgumentsMapper integerArgumentsMapper;

	public RandFunction() {
		super(1, 2, InternalFunction.RAND_FUNCTION);
	}

	@Override
	public ObjectData call(ObjectData[] datas) {
		List<Integer> arguments = integerArgumentsMapper.mapToIntArgs(datas, this);
		int rows = arguments.get(0);
		int columns = arguments.size() == 2 ? arguments.get(1) : rows;
		return matrixFactory.createRandDouble(rows, columns);
	}

}
