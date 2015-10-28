package interpreter.commons.exception;

public class IllegalArgumentException extends InterpreterException {

	private static final long serialVersionUID = 1L;
	public static final String ARGUMENT_MUST_BE_NON_NEGATIVE_INTEGER = "Argument must be non-negative integer";

	public IllegalArgumentException() {
		super();
	}

	public IllegalArgumentException(String message) {
		super(message);
	}

}
