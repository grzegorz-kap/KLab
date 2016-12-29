package com.klab.interpreter.commons.memory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Service;

@Service
public class IdentifierMapperImpl implements IdentifierMapper {
    private Deque<AddressMap> scopes = new ArrayDeque<>();
    private AddressMap internalFunctionAddressMap = new AddressMap();
    private AddressMap externalFunctionAddressMap = new AddressMap();
    private AddressMap mainIdentifierAddressMap = new AddressMap();
    
    private static class AddressMap {
    	private int freeAddress = 0;
    	private Map<String, Integer> map = new HashedMap<>();
    	
    	public Integer register(final String id) {
    		return map.computeIfAbsent(id, key -> {
    			return freeAddress++;
    		});
    	}
    	
    	public Integer get(final String key) {
    		return map.get(key);
    	}
    	
    	public int size() {
    		return map.size();
    	}
    }

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
