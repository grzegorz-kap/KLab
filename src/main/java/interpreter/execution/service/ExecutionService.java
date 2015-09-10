package interpreter.execution.service;

import interpreter.execution.model.ExecutionContext;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.Instruction;

import java.util.Collection;

public class ExecutionService {

    private ExecutionContext executionContext;
    private InstructionPointer instructionPointer;

    public ExecutionService(){
        executionContext = new ExecutionContext();
        instructionPointer = executionContext.newInstructionPointer();
    }

    public void addInstructions(Collection<? extends Instruction> instructions) {
        executionContext.addInstruction(instructions);
    }


}
