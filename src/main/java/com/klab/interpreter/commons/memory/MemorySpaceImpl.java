package com.klab.interpreter.commons.memory;

import com.klab.interpreter.types.ObjectData;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class MemorySpaceImpl implements MemorySpace {
    private ObjectWrapper[] memory = new ObjectWrapper[0];
    private Deque<ObjectWrapper[]> stack = new ArrayDeque<>();
    private Deque<Integer> scopesIds = new ArrayDeque<>();
    private int scopeId = 0;
    private int sequence = 0;

    @Override
    public int scopeId() {
        return scopeId;
    }

    @Override
    public int find(ObjectData objectData) {
        for (int index = memory.length - 1; index >= 0; index--) {
            if (memory[index].data == objectData) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public void newScope(ObjectData[] data) {
        scopesIds.addFirst(scopeId);
        scopeId = ++sequence;
        stack.addFirst(memory);
        if (data != null) {
            memory = new ObjectWrapper[data.length];
            for (int i = 0; i < data.length; i++) {
                memory[i] = new ObjectWrapper(data[i], i);
            }
        } else {
            memory = new ObjectWrapper[0];
        }
    }

    @Override
    public void previousScope() {
        scopeId = scopesIds.removeFirst();
        memory = stack.removeFirst();
    }

    @Override
    public void reserve(int size) {
        if (size > memory.length) {
            ObjectWrapper[] extend = new ObjectWrapper[size - memory.length];
            for (int i = 0; i < extend.length; i++) {
                extend[i] = new ObjectWrapper(null, memory.length + i);
            }
            memory = ArrayUtils.addAll(memory, extend);
        }
    }

    @Override
    public ObjectData set(int address, ObjectData data, String name) {
        ObjectWrapper wrapper = memory[address];
        if (data != null) {
            wrapper.data = data.isTemp() ? data : data.copy();
            wrapper.data.setTemp(false);
            wrapper.data.setName(name);
        } else {
            wrapper.data = null;
        }
        wrapper.version++;
        return wrapper.data;
    }

    @Override
    public ObjectData find(String name) {
        return Stream.of(memory)
                .map(ObjectWrapper::getData)
                .filter(Objects::nonNull)
                .filter(objectData -> name.equals(objectData.getName()))
                .findFirst().orElse(null);
    }

    @Override
    public ObjectData get(int address) {
        return memory[address].data;
    }

    @Override
    public ObjectData getForModify(int address) {
        ObjectWrapper wrapper = memory[address];
        wrapper.version++;
        return wrapper.data;
    }

    @Override
    public Stream<ObjectWrapper> listCurrentScopeVariables() {
        return Stream.of(memory);
    }

    @Override
    public int size() {
        return memory.length;
    }
}
