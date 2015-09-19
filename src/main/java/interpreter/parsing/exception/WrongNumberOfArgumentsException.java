package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class WrongNumberOfArgumentsException extends RuntimeException {

    private ParseContext parseContext;

    public WrongNumberOfArgumentsException(String message, ParseContext parseContext) {
        super(message);
        this.parseContext = parseContext;
    }
}
