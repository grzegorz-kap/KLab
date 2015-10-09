package interpreter.parsing.model.tokens;

import interpreter.commons.VariableScope;
import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class IdentifierToken extends ParseToken {

    private VariableScope variableScope;
    private String variableName;
    private Integer address;

    public IdentifierToken(Token token) {
        variableName = token.getLexeme();
        setToken(token);
        setParseClass(ParseClass.IDENTIFIER);
    }

    public VariableScope getVariableScope() {
        return variableScope;
    }

    public void setVariableScope(VariableScope variableScope) {
        this.variableScope = variableScope;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }
}
