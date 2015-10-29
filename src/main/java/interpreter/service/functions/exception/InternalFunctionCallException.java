package interpreter.service.functions.exception;

import interpreter.commons.exception.InterpreterException;
import interpreter.service.functions.InternalFunction;

public class InternalFunctionCallException extends InterpreterException {
	
	private InternalFunction internalFunction;
	
	public InternalFunctionCallException(InternalFunction internalFunction) {
		this.internalFunction = internalFunction;
	}
	
	public InternalFunctionCallException(InternalFunction internalFunction, Throwable cause) {
		super(cause);
		this.internalFunction = internalFunction;
	}

}
