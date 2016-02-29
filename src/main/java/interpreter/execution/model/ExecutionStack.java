package interpreter.execution.model;

import interpreter.types.ObjectData;

import java.util.ArrayDeque;
import java.util.Deque;

class ExecutionStack {
    private Deque<ObjectData> stack = new ArrayDeque<>();

    public void push(ObjectData objectData) {
        if (objectData != null) {
            stack.addFirst(objectData);
        }
    }

    public ObjectData pop() {
        return stack.removeFirst();
    }

    public ObjectData peek() {
        return stack.peekFirst();
    }

    public int size() {
        return stack.size();
    }

    public void clear() {
        stack.clear();
    }
}
