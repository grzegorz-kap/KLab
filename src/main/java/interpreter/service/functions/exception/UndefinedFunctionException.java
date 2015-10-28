package interpreter.service.functions.exception;

import interpreter.service.functions.model.CallInstruction;

public class UndefinedFunctionException extends RuntimeException {

	private static final long serialVersionUID = 834347388412197049L;

	public static final String MESSAGE_FORMAT = "No function '%s' found which takes '%d' arguments";

	private CallInstruction instruction;

	public UndefinedFunctionException(CallInstruction callInstruction) {
		this.instruction = callInstruction;
	}

	@Override
	public String toString() {
		return String.format(MESSAGE_FORMAT, instruction.getName(), instruction.getArgumentsNumber());
	}
	
	@Override
	public String getMessage() {
		return toString();
	}

}
