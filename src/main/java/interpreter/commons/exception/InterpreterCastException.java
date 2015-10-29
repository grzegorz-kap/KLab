package interpreter.commons.exception;

public class InterpreterCastException extends InterpreterException {
	
	public static final String COMPLEX_LOGICALS = "Complex values cannot be converted to logicals.";

	public InterpreterCastException() {
		super();
	}

	public InterpreterCastException(String message) {
		super(message);
	}

	public InterpreterCastException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 1L;

}
