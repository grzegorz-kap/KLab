package interpreter.types;

import interpreter.parsing.model.tokens.IdentifierToken;

public class TokenIdentifierObject extends AbstractObjectData implements IdentifierObject {
    private IdentifierToken identifierToken;
    private boolean canBeScript = false;

    public TokenIdentifierObject(IdentifierToken identifierToken) {
        this.identifierToken = identifierToken;
    }

    public TokenIdentifierObject(String name, Integer address) {
        identifierToken = new IdentifierToken();
        identifierToken.setId(name);
        identifierToken.setAddress(address);
    }

    @Override
    public boolean isCanBeScript() {
        return canBeScript;
    }

    @Override
    public void setCanBeScript(boolean canBeScript) {
        this.canBeScript = canBeScript;
    }

    @Override
    public String toString() {
        return identifierToken.getId() + "@" + identifierToken.getAddress();
    }

    @Override
    public ObjectData copyObjectData() {
        return new TokenIdentifierObject(identifierToken);
    }

    @Override
    public int getAddress() {
        return identifierToken.getAddress();
    }

    @Override
    public void setAddress(int address) {
        this.identifierToken.setAddress(address);
    }

    @Override
    public String getId() {
        return identifierToken.getId();
    }

    @Override
    public boolean isTrue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setId(String id) {
        this.identifierToken.setId(id);
    }
}
