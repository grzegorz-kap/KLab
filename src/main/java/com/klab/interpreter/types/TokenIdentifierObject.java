package com.klab.interpreter.types;

import com.klab.interpreter.parsing.model.tokens.IdentifierToken;

public class TokenIdentifierObject extends AbstractObjectData implements IdentifierObject {
    private String id;
    private Integer address;
    private boolean canBeScript = false;

    public TokenIdentifierObject(String name, Integer address) {
        this.id = name;
        this.address = address;
    }

    public TokenIdentifierObject(IdentifierToken identifierToken) {
        this.id = identifierToken.getId();
        this.address = identifierToken.getMemAddress();
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
        return id + "@" + address;
    }

    @Override
    public ObjectData copyObjectData() {
        return new TokenIdentifierObject(id, address);
    }

    @Override
    public int getAddress() {
        return address;
    }

    @Override
    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isTrue() {
        throw new UnsupportedOperationException();
    }
}
