package interpreter.commons.exception;

public class InterpreterCastException extends InterpreterException {

    public static final String COMPLEX_LOGICALS = "Complex values cannot be converted to logicals.";
    private static final long serialVersionUID = 1L;

    public InterpreterCastException() {
        super();
    }

    public InterpreterCastException(String message) {
        super(message);
    }

    public InterpreterCastException(Throwable cause) {
        super(cause);
    }

}
