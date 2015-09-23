package interpreter.parsing.model;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;

public class ParseToken {

    private Token token;
    private ParseClass parseClass;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public TokenClass getTokenClass() {
        return token.getTokenClass();
    }

    public ParseClass getParseClass() {
        return parseClass;
    }

    public void setParseClass(ParseClass parseClass) {
        this.parseClass = parseClass;
    }
}
