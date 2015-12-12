package interpreter.types;

import interpreter.parsing.model.tokens.IdentifierToken;

public class IdentifierObject extends AbstractObjectData {
    private IdentifierToken identifierToken;
    private boolean canBeScript = false;

    public IdentifierObject(IdentifierToken identifierToken) {
        this.identifierToken = identifierToken;
    }

    public IdentifierObject(String name, Integer address) {
        identifierToken = new IdentifierToken();
        identifierToken.setId(name);
        identifierToken.setAddress(address);
    }

    public boolean isCanBeScript() {
        return canBeScript;
    }

    public void setCanBeScript(boolean canBeScript) {
        this.canBeScript = canBeScript;
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

    @Override
    public boolean isTrue() {
        throw new UnsupportedOperationException();
    }
}
