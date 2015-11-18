package interpreter.parsing.model.tokens;

import interpreter.commons.VariableScope;
import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class IdentifierToken extends ParseToken {

    private VariableScope variableScope;
    private String id;
    private Integer address;

    public IdentifierToken() {
        setParseClass(ParseClass.IDENTIFIER);
    }

    public IdentifierToken(Token token) {
        this();
        id = token.getLexeme();
        setToken(token);
    }

    public VariableScope getVariableScope() {
        return variableScope;
    }

    public void setVariableScope(VariableScope variableScope) {
        this.variableScope = variableScope;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }
}
