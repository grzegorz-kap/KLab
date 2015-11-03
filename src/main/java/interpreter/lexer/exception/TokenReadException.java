package interpreter.lexer.exception;

import interpreter.lexer.model.TokenizerContext;

public class TokenReadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenReadException(String message, TokenizerContext tokenizerContext) {
        super(message);
    }
}
