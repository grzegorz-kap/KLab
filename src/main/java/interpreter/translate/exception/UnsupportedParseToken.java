package interpreter.translate.exception;

import interpreter.parsing.model.ParseToken;

public class UnsupportedParseToken extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ParseToken parseToken;

    public UnsupportedParseToken(String message, ParseToken parseToken) {
        super(message);
        this.parseToken = parseToken;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ", " + parseToken.getParseClass() + ", " + parseToken.getToken().getLexeme();
    }
}
