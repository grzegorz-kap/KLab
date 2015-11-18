package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class WrongForInstructionException extends ParseException {

    public static final String FOR_KEYWORD_NOT_ALLOWED_HERE = "'for' keyword not allowed here.";
    public static final String ENDFOR_KEYWORD_NOT_EXPECTED_HERE = "'endfor' keyword not expected here";

    public WrongForInstructionException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
