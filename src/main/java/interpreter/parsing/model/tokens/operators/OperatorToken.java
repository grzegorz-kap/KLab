package interpreter.parsing.model.tokens.operators;

import interpreter.parsing.model.ParseToken;

public class OperatorToken extends ParseToken {

    private int argumentsNumber;
    private OperatorPriority operatorPriority;
    private OperatorAssociativity operatorAssociativity;

    public OperatorToken() {

    }

    public OperatorToken(int argumentsNumber, OperatorAssociativity operatorAssociativity, OperatorPriority operatorPriority) {
        this.argumentsNumber = argumentsNumber;
        this.operatorAssociativity = operatorAssociativity;
        this.operatorPriority = operatorPriority;
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
}
