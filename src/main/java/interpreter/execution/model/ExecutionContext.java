package interpreter.execution.model;

import interpreter.translate.model.instruction.Instruction;

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
}
