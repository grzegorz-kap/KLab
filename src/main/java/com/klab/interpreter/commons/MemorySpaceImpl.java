package com.klab.interpreter.commons;

import com.klab.interpreter.types.ObjectData;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;

@Service
public class MemorySpaceImpl implements MemorySpace {
    private ObjectData[] mainIdentifiers = new ObjectData[0];
    private Deque<ObjectData[]> stack = new ArrayDeque<>();

    @Override
    public void newScope(ObjectData[] data) {
        stack.addFirst(mainIdentifiers);
        mainIdentifiers = data;
    }

    @Override
    public void previousScope() {
        mainIdentifiers = stack.removeFirst();
    }

    @Override
    public void reserve(int size) {
        if (size > mainIdentifiers.length) {
            mainIdentifiers = ArrayUtils.addAll(mainIdentifiers, new ObjectData[size - mainIdentifiers.length]);
        }
    }

    @Override
    public void set(int address, ObjectData data) {
        mainIdentifiers[address] = data;
    }

    @Override
    public ObjectData get(int address) {
        return mainIdentifiers[address];
    }
}
