package com.klab.interpreter.parsing.model.tokens.operators;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;

public class OperatorToken extends ParseToken {
    private int argumentsNumber;
    private OperatorPriority operatorPriority;
    private OperatorAssociativity operatorAssociativity;
    private OperatorCode operatorCode;

    public OperatorToken(Token token) {
        super(token, ParseClass.OPERATOR);
    }

    public OperatorToken(Token token, int argumentsNumber, OperatorAssociativity operatorAssociativity, OperatorPriority operatorPriority, OperatorCode operatorCode) {
        this(token);
        this.argumentsNumber = argumentsNumber;
        this.operatorAssociativity = operatorAssociativity;
        this.operatorPriority = operatorPriority;
        this.operatorCode = operatorCode;
    }

    public int getArgumentsNumber() {
        return argumentsNumber;
    }

    public void setArgumentsNumber(int argumentsNumber) {
        this.argumentsNumber = argumentsNumber;
    }

    public OperatorPriority getPriority() {
        return operatorPriority;
    }

    public void setOperatorPriority(OperatorPriority operatorPriority) {
        this.operatorPriority = operatorPriority;
    }

    public OperatorAssociativity getAssociativity() {
        return operatorAssociativity;
    }

    public void setOperatorAssociativity(OperatorAssociativity operatorAssociativity) {
        this.operatorAssociativity = operatorAssociativity;
    }

    public OperatorCode getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(OperatorCode operatorCode) {
        this.operatorCode = operatorCode;
    }
}
