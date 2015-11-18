package interpreter.parsing.exception;

import interpreter.parsing.model.ParseContext;

public class UnexpectedCloseParenthesisException extends ParseException {

    private static final long serialVersionUID = 1L;

    public UnexpectedCloseParenthesisException(String message, ParseContext parseContext) {
        super(message, parseContext);
    }
}
