package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class WrongNumberOfArgumentsException extends ParseException {

	private static final long serialVersionUID = 1L;

	public WrongNumberOfArgumentsException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
