package interpreter.InstructionKeyword.exception;

public class KeywordParseException extends RuntimeException	{

	public static final String UNEXPECTED_ELSE_OR_ELSEIF = "Unexpected 'else' or 'elseif'";

	public KeywordParseException(String message) {
		super(message);
	}
}
