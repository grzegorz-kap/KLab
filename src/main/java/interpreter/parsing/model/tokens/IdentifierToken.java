package interpreter.parsing.model.tokens;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class IdentifierToken extends ParseToken {
    private String id;
    private int address;

    public IdentifierToken() {
        setParseClass(ParseClass.IDENTIFIER);
    }

    public IdentifierToken(Token token) {
        this();
        id = token.getLexeme();
        setToken(token);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
