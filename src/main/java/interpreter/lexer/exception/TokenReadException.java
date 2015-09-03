package interpreter.lexer.exception;

import interpreter.lexer.model.TokenizerContext;

public class TokenReadException extends RuntimeException {

    private TokenizerContext tokenizerContext;

    public TokenReadException(String message, TokenizerContext tokenizerContext) {
        super(message);
        this.tokenizerContext = tokenizerContext;
    }
}
