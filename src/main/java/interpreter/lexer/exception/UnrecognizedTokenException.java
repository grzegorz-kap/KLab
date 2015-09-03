package interpreter.lexer.exception;

import interpreter.lexer.model.TokenizerContext;

public class UnrecognizedTokenException extends RuntimeException {

    private TokenizerContext tokenizerContext;

    public UnrecognizedTokenException(String message, TokenizerContext tokenizerContext) {
        super(message);
        this.tokenizerContext = tokenizerContext;
    }
}
