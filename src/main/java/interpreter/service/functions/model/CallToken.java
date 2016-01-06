package interpreter.service.functions.model;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

public class CallToken extends ParseToken {
    private Integer variableAddress;
    private Integer externalAddress;
    private Integer internalFunctionAddress;

    public CallToken(Token token) {
        setToken(token);
        setParseClass(ParseClass.CALL);
    }

    public Integer getExternalAddress() {
        return externalAddress;
    }

    public void setExternalAddress(Integer externalAddress) {
        this.externalAddress = externalAddress;
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
