package interpreter.types;

import interpreter.parsing.model.tokens.IdentifierToken;

public class IdentifierObject extends AbstractObjectData {

    private IdentifierToken identifierToken;

    public IdentifierObject(IdentifierToken identifierToken) {
        this.identifierToken = identifierToken;
    }

    public IdentifierToken getIdentifierToken() {
        return identifierToken;
    }

    @Override
    public String toString() {
        return identifierToken.getId() + "@" + identifierToken.getAddress();
    }

    @Override
    public ObjectData copyObjectData() {
        return new IdentifierObject(identifierToken);
    }

    public Integer getAddress() {
        return identifierToken.getAddress();
    }

    public String getId() {
        return identifierToken.getId();
    }
}
