package interpreter.execution.exception;

import interpreter.types.IdentifierObject;

public class UndefinedVariableException extends RuntimeException {

    private IdentifierObject identifierObject;

    public UndefinedVariableException(String message, IdentifierObject identifierObject) {
        super(message);
        this.identifierObject = identifierObject;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": '" + identifierObject.getId() + "'";
    }
}
