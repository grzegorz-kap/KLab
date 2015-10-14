package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class UnexpectedCloseParenthesisException extends ParseException {

    public UnexpectedCloseParenthesisException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
