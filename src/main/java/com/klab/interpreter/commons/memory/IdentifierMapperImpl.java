package com.klab.interpreter.commons.memory;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

@Service
public class IdentifierMapperImpl implements IdentifierMapper {
    private Deque<Map<String, Integer>> scopes = new ArrayDeque<>();
    private Deque<Integer> scopesAddress = new ArrayDeque<>();

    private int internalFunctionFreeAddress = 0;
    private Map<String, Integer> internalFunctionAddressMap = Maps.newHashMap();

    private int externalFunctionFreeAddress = 0;
    private Map<String, Integer> externalFunctionAddressMap = Maps.newHashMap();

    private int mainIdentifierFreeAddress = 0;
    private Map<String, Integer> mainIdentifierAddressMap = Maps.newHashMap();

    @Override
    public Integer registerMainIdentifier(String id) {
        return mainIdentifierAddressMap.computeIfAbsent(id, name -> {
            return mainIdentifierFreeAddress++;
        });
    }

    @Override
    public Integer registerExternalFunction(String id) {
        return externalFunctionAddressMap.computeIfAbsent(id, name -> {
            return externalFunctionFreeAddress++;
        });
    }

    @Override
    public void putNewScope() {
        scopes.addFirst(mainIdentifierAddressMap);
        scopesAddress.addFirst(mainIdentifierFreeAddress);
        mainIdentifierAddressMap = Maps.newHashMap();
        mainIdentifierFreeAddress = 0;
    }

    @Override
    public void restorePreviousScope() {
        mainIdentifierAddressMap = scopes.removeFirst();
        mainIdentifierFreeAddress = scopesAddress.removeFirst();
    }

    @Override
    public int mainMappingsSize() {
        return mainIdentifierAddressMap.size();
    }

    @Override
    public Integer registerInternalFunction(String id) {
        return internalFunctionAddressMap.computeIfAbsent(id, name -> internalFunctionFreeAddress++);
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
