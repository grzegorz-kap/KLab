package com.klab.interpreter.commons;

public interface IdentifierMapper {
    Integer registerMainIdentifier(String id);

    Integer getMainAddress(String id);

    Integer registerInternalFunction(String id);

    Integer registerExternalFunction(String id);

    Integer getExternalAddress(String id);

    void putNewScope();

    void restorePreviousScope();

    int mainMappingsSize();
}