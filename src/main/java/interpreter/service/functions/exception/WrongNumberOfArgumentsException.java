package interpreter.service.functions.exception;

import interpreter.commons.exception.InterpreterException;
import interpreter.service.functions.InternalFunction;
import interpreter.service.functions.model.CallInstruction;

public class WrongNumberOfArgumentsException extends InterpreterException {
	
	private static final String MESSAGE_FORMAT = "Wrong number of argumens in '%s' function call.";
	private InternalFunction internalFunction;

	public WrongNumberOfArgumentsException(InternalFunction internalFunction) {
		this.internalFunction = internalFunction;
	}
	
	@Override
	public String getMessage() {
		return String.format(MESSAGE_FORMAT, internalFunction.getName());
	}

}
