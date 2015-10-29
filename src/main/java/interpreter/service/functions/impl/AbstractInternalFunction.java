package interpreter.service.functions.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import interpreter.service.functions.IntegerArgumentsMapper;
import interpreter.service.functions.InternalFunction;
import interpreter.types.ObjectData;
import interpreter.types.matrix.MatrixFactory;

public abstract class AbstractInternalFunction implements InternalFunction {

	@Autowired
	protected MatrixFactory matrixFactory;
	
	@Autowired
	protected IntegerArgumentsMapper integerArgumentsMapper;
	
	private Integer address;
	private int argsMin;
	private int argsMax;
	private String name;

	public AbstractInternalFunction(int argsMin,int argsMax, String name) {
		this.argsMin = argsMin;
		this.argsMax = argsMax;
		this.name = name;
	}
	
	protected ObjectData createMatrix(ObjectData[] datas, MatrixCreator creator) {
		List<Integer> arguments =  integerArgumentsMapper.mapToIntArgs(datas, this);
		int rows = arguments.get(0);
		int columns = arguments.size() == 2 ? arguments.get(1) : rows;
		return creator.create(rows, columns);
		
	}
	
	@Override
	public int getMinArgs() {
		return argsMin;
	}

	@Override
	public int getMaxArgs() {
		return argsMax;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getAddress() {
		return address;
	}

	@Override
	public void setAddress(Integer address) {
		this.address = address;
	}
}
