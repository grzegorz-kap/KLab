package interpreter.execution.model;

import interpreter.translate.model.Instruction;
import interpreter.types.ObjectData;

import java.util.Collection;

public class ExecutionContext {

    private Code code = new Code();
    private ExecutionStack executionStack = new ExecutionStack();

    public InstructionPointer newInstructionPointer() {
        return new InstructionPointer(code);
    }

    public void addInstruction(Collection<? extends Instruction> instructions) {
        code.add(instructions);
    }

    public void setCode(Code code) {
        this.code = code;
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

    public ObjectData executionStackPop() {
        return executionStack.pop();
    }

    public void executionStackPush(ObjectData objectData) {
        executionStack.push(objectData);
    }
}
