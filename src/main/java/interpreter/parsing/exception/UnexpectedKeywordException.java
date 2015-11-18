package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class UnexpectedKeywordException extends ParseException {
    public UnexpectedKeywordException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
