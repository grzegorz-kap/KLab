package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class UnexpectedFunctionArgumentsDelimiter extends ParseException {
	
	private static final long serialVersionUID = 1L;
	public static final String UNEXPECTED_ARGUMENT_DELIMITER = "Function arguments delimiter found outside function call";

	public UnexpectedFunctionArgumentsDelimiter(String message, ParseContext parseContext) {
		super(message, parseContext);
	}

}