package com.klab.interpreter.commons.memory;

import java.util.ArrayDeque;
import java.util.Deque;

import org.springframework.stereotype.Service;

@Service
public class IdentifierMapperImpl implements IdentifierMapper {
    private Deque<AddressMap> scopes = new ArrayDeque<>();
    private AddressMap internalFunctionAddressMap = new AddressMap();
    private AddressMap externalFunctionAddressMap = new AddressMap();
    private AddressMap mainIdentifierAddressMap = new AddressMap();

    @Override
    public Integer registerMainIdentifier(String id) {
        return mainIdentifierAddressMap.register(id);
    }

    @Override
    public Integer registerExternalFunction(String id) {
       return externalFunctionAddressMap.register(id);
    }

    @Override
    public void putNewScope() {
        scopes.addFirst(mainIdentifierAddressMap);
        mainIdentifierAddressMap = new AddressMap();
    }

    @Override
    public void restorePreviousScope() {
        mainIdentifierAddressMap = scopes.removeFirst();
    }

    @Override
    public int mainMappingsSize() {
        return mainIdentifierAddressMap.size();
    }

    @Override
    public Integer registerInternalFunction(String id) {
        return internalFunctionAddressMap.register(id);
    }

    @Override
    public Integer getMainAddress(String id) {
        return mainIdentifierAddressMap.get(id);
    }

    @Override
    public Integer getExternalAddress(String id) {
        return externalFunctionAddressMap.get(id);
    }
}
