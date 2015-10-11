package interpreter.types;

import interpreter.parsing.model.tokens.IdentifierToken;

public class IdentifierObject implements ObjectData {

    private IdentifierToken identifierToken;

    public IdentifierObject(IdentifierToken identifierToken) {
        this.identifierToken = identifierToken;
    }

    public IdentifierToken getIdentifierToken() {
        return identifierToken;
    }

    @Override
    public String toString() {
        return identifierToken.getParseClass() + " " + identifierToken.getId() + " " + identifierToken.getAddress();
    }

    @Override
    public ObjectData copyObjectData() {
        return new IdentifierObject(identifierToken);
    }
}
