package interpreter.parsing.model.tokens;

import interpreter.commons.VariableScope;
import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class IdentifierToken extends ParseToken {

    private VariableScope variableScope;
    private String id;
    private Integer address;
    private Integer internalFunctionAddress;

    public IdentifierToken(Token token) {
        id = token.getLexeme();
        setToken(token);
        setParseClass(ParseClass.IDENTIFIER);
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

    public Integer getInternalFunctionAddress() {
        return internalFunctionAddress;
    }

    public void setInternalFunctionAddress(Integer internalFunctionAddress) {
        this.internalFunctionAddress = internalFunctionAddress;
    }
}
