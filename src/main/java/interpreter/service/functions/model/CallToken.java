package interpreter.service.functions.model;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class CallToken extends ParseToken {

    private Integer variableAddress;
    private Integer internalFunctionAddress;

    public CallToken(Token token) {
        setToken(token);
        setParseClass(ParseClass.CALL);
    }

    public String getCallName() {
        return getToken().getLexeme();
    }

    public Integer getVariableAddress() {
        return variableAddress;
    }

    public void setVariableAddress(Integer variableAddress) {
        this.variableAddress = variableAddress;
    }

    public Integer getInternalFunctionAddress() {
        return internalFunctionAddress;
    }

    public void setInternalFunctionAddress(Integer internalFunctionAddress) {
        this.internalFunctionAddress = internalFunctionAddress;
    }
}
