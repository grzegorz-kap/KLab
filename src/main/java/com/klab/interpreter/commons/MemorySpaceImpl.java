package com.klab.interpreter.commons;

import com.klab.interpreter.types.ObjectData;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class MemorySpaceImpl implements MemorySpace {
    private ObjectData[] memory = new ObjectData[0];
    private Deque<ObjectData[]> stack = new ArrayDeque<>();

    @Override
    public void newScope(ObjectData[] data) {
        stack.addFirst(memory);
        memory = data;
    }

    @Override
    public void previousScope() {
        memory = stack.removeFirst();
    }

    @Override
    public void reserve(int size) {
        if (size > memory.length) {
            memory = ArrayUtils.addAll(memory, new ObjectData[size - memory.length]);
        }
    }

    @Override
    public void set(int address, ObjectData data) {
        memory[address] = data;
    }

    @Override
    public void set(int address, ObjectData data, String name) {
        memory[address] = data;
        data.setName(name);
    }

    @Override
    public ObjectData get(int address) {
        return memory[address];
    }

    @Override
    public Stream<ObjectData> listCurrentScopeVariables() {
        return IntStream.range(0, memory.length)
                .filter(idx -> memory[idx] != null)
                .filter(idx -> memory[idx].getName() != null)
                .peek(idx -> memory[idx].setAddress(idx))
                .mapToObj(idx -> memory[idx]);
    }
}
