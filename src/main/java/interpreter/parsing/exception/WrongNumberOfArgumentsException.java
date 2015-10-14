package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class WrongNumberOfArgumentsException extends ParseException {

    public WrongNumberOfArgumentsException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
