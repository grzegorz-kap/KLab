package interpreter.commons;

import interpreter.types.ObjectData;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;

@Service
public class MemorySpace {
    private ObjectData[] mainIdentifiers = new ObjectData[0];
    private Deque<ObjectData[]> stack = new ArrayDeque<>();

    public void newScope(ObjectData[] data) {
        stack.addFirst(mainIdentifiers);
        mainIdentifiers = data;
    }

    public void previousScope() {
        mainIdentifiers = stack.removeFirst();
    }

    public void reserve(int size) {
        if (size > mainIdentifiers.length) {
            mainIdentifiers = ArrayUtils.addAll(mainIdentifiers, new ObjectData[size - mainIdentifiers.length]);
        }
    }

    public void set(int address, ObjectData data) {
        mainIdentifiers[address] = data;
    }

    public ObjectData get(int address) {
        return mainIdentifiers[address];
    }
}
