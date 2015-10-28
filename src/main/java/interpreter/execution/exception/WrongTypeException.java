package interpreter.execution.exception;

import interpreter.types.ObjectData;

public class WrongTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public WrongTypeException(ObjectData objectData) {
    }
}
