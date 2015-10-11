package interpreter.execution.model;

import interpreter.types.ObjectData;

import java.util.ArrayDeque;
import java.util.Deque;

class ExecutionStack {

    private Deque<ObjectData> stack = new ArrayDeque<>();

    public void push(ObjectData objectData) {
        stack.push(objectData);
    }

    public ObjectData pop() {
        return stack.pop();
    }

    public ObjectData peek() {
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }

    public void clear() {
        stack.clear();
    }
}
