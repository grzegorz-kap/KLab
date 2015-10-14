package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class WrongIfInstructionException extends ParseException {

    public static final String UNEXPECTED_IF_OR_ELSEIF_KEYWORD = "Unexpected 'if' or 'elseif' keyword.";

    public WrongIfInstructionException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
