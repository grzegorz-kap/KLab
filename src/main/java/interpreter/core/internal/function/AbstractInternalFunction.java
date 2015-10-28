package interpreter.core.internal.function;

public class AbstractInternalFunction {
	private Integer address;
	private int argumentsNumber;
	private String name;

	public AbstractInternalFunction(Integer address, int argumentsNumber, String name) {
		this.address = address;
		this.argumentsNumber = argumentsNumber;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getArgumentsNumber() {
		return argumentsNumber;
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
		this.address = address;
	}
}
