package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class WrongIfInstructionException extends ParseException {

    public static final String UNEXPECTED_IF_OR_ELSEIF_KEYWORD = "Unexpected 'if' or 'elseif' keyword.";
    public static final String KEYWORD_ENDIF_NOT_EXPECTED_HERE = "Keyword 'endif' not expected here.";
    public static final String KEYWORD_ENDIF_CANNOT_BE_USED_WITHOUT_IF = "Keyword 'endif' cannot be used without 'if'.";
	public static final String ELSE_KEYWORD_CAN_T_BE_USED_WITHOUT_IF = "'else' keyword can't be used without 'if'";
	public static final String KEYWORD_ELSE_NOT_EXPECTED_HERE = "Keyword 'else' not expected here";
	public static final String ELSEIF_NOT_EXPECTED_HERE = "'elseif' not expected here";

    public WrongIfInstructionException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
