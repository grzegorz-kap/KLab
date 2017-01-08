package com.klab.interpreter.commons.memory;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

/**
 * Created by grzk on 29.12.2016.
 */
class AddressMap {
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
