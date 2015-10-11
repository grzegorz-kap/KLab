package interpreter.math;

import interpreter.commons.ObjectData;
import interpreter.parsing.model.tokens.IdentifierToken;

public class IdentifierObject implements ObjectData {

    private IdentifierToken identifierToken;

    public IdentifierObject(IdentifierToken identifierToken) {
        this.identifierToken = identifierToken;
    }

    public IdentifierToken getIdentifierToken() {
        return identifierToken;
    }
}
