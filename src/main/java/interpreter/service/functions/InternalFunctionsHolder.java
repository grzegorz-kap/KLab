package interpreter.service.functions;

import interpreter.service.functions.model.InternalFunction;

public interface InternalFunctionsHolder {
	InternalFunction get(int address);

	Integer getAddress(String functionName, int argumentsNumber);

	boolean contains(String functionName, int argumentsNumber);

	boolean contains(String functionName);
}
