package interpreter.InstructionKeyword.exception;

public class KeywordParseException extends RuntimeException	{

	private static final long serialVersionUID = 1L;
	public static final String UNEXPECTED_ELSE_OR_ELSEIF = "Unexpected 'else' or 'elseif'";

	public KeywordParseException(String message) {
		super(message);
	}
}
