package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public abstract class ParseException extends RuntimeException {

    protected ParseContext parseContext;

    public ParseException(String message, ParseContext parseContext) {
        super(message);
        this.parseContext = parseContext;
    }
}
