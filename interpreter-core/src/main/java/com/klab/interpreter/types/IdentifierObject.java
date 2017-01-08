package com.klab.interpreter.types;

public interface IdentifierObject extends ObjectData {
    boolean isCanBeScript();

    void setCanBeScript(boolean canBeScript);

    int getAddress();

    void setAddress(int address);

    String getId();

    void setId(String id);
}
