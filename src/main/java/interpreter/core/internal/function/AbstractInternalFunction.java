package interpreter.core.internal.function;

import interpreter.service.functions.model.InternalFunction;

public abstract class AbstractInternalFunction implements InternalFunction {
	
	private Integer address;
	private int args;
	private String name;

	public AbstractInternalFunction(int args, String name) {
		this.args = args;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getArgumentsNumber() {
		return args;
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
