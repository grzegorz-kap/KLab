package interpreter.commons.exception;

public class InterpreterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InterpreterException() {
    }

    public InterpreterException(String message) {
        super(message);
    }

    public InterpreterException(Throwable cause) {
        super(cause);
    }

}
