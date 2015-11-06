package interpreter.InstructionKeyword.exception;

public class KeywordParseException extends RuntimeException {

    public static final String UNEXPECTED_ELSE_OR_ELSEIF = "Unexpected 'else' or 'elseif'";
    public static final String FOR_KEYWORD_ASSIGNMENT_EXPECTED = "Single value assignment expected after 'for' keyword";
    private static final long serialVersionUID = 1L;

    public KeywordParseException(String message) {
        super(message);
    }
}
