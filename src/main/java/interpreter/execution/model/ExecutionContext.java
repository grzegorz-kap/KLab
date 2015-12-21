package interpreter.execution.model;

import interpreter.translate.model.Instruction;
import interpreter.types.ObjectData;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class ExecutionContext {
    private Code code = new Code();
    private ExecutionStack executionStack = new ExecutionStack();
    private Deque<Integer> deque = new ArrayDeque<>();

    public void storeExecutionStackSize() {
        deque.addFirst(executionStackSize());
    }

    public void restoreExecutionStackSize() {
        int elementsToPop = executionStack.size() - deque.removeFirst();
        if(elementsToPop < 0) {
            throw new RuntimeException("Error in external function"); // TODO better
        }
        while (elementsToPop-- > 0) {
            executionStackPop();
        }
    }

    public InstructionPointer newInstructionPointer() {
        return new InstructionPointer(code);
    }

    public void addInstruction(Collection<? extends Instruction> instructions) {
        code.add(instructions);
    }

    public void setExecutionStack(ExecutionStack executionStack) {
        this.executionStack = executionStack;
    }

    public void clearExecutionStack() {
        executionStack.clear();
    }

    public void clearCode() {
        code.clear();
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public ObjectData executionStackPop() {
        return executionStack.pop();
    }

    public void executionStackPush(ObjectData objectData) {
        executionStack.push(objectData);
    }
    
    public ObjectData executionStackPeek() {
    	return executionStack.peek();
    }

    public int executionStackSize() {
        return executionStack.size();
    }
}
