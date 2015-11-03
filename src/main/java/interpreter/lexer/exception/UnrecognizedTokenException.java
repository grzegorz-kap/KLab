package interpreter.lexer.exception;

import interpreter.lexer.model.TokenizerContext;

public class UnrecognizedTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private TokenizerContext tokenizerContext;

    public UnrecognizedTokenException(String message, TokenizerContext tokenizerContext) {
        super(message);
        this.tokenizerContext = tokenizerContext;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.getMessage());
        builder.append(":'");
        builder.append(tokenizerContext.charAt(0));
        builder.append("'. ");
        builder.append(" Line: ").append(tokenizerContext.getLine());
        builder.append(". Column: ").append(tokenizerContext.getColumn());
        return builder.toString();
    }
}
