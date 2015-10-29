package interpreter.service.functions.matrix.generation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import interpreter.service.functions.AbstractInternalFunction;
import interpreter.service.functions.IntegerArgumentsMapper;
import interpreter.types.ObjectData;
import interpreter.types.matrix.MatrixFactory;

public abstract class AbstractMatrixGeneratorFunction extends AbstractInternalFunction {

	@Autowired
	protected MatrixFactory matrixFactory;

	@Autowired
	protected IntegerArgumentsMapper integerArgumentsMapper;

	public AbstractMatrixGeneratorFunction(int argsMin, int argsMax, String name) {
		super(argsMin, argsMax, name);
	}

	protected ObjectData createMatrix(ObjectData[] datas, MatrixCreator creator) {
		List<Integer> arguments = integerArgumentsMapper.mapToIntArgs(datas, this);
		int rows = arguments.get(0);
		int columns = arguments.size() == 2 ? arguments.get(1) : rows;
		return creator.create(rows, columns);

	}

}
