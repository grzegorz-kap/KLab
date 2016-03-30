package com.klab.interpreter.commons.memory;

import com.klab.interpreter.types.ObjectData;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

@Service
public class MemorySpaceImpl implements MemorySpace {
    private ObjectWrapper[] memory = new ObjectWrapper[0];
    private Deque<ObjectWrapper[]> stack = new ArrayDeque<>();
    private int scopeId = 0;

    @Override
    public int scopeId() {
        return scopeId;
    }

    @Override
    public void newScope(ObjectData[] data) {
        scopeId++;
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
        scopeId--;
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
    public void set(int address, ObjectData data) {
        ObjectWrapper wrapper = memory[address];
        wrapper.data = data;
        wrapper.version++;
    }

    @Override
    public void set(int address, ObjectData data, String name) {
        set(address, data);
        data.setName(name);
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
}
