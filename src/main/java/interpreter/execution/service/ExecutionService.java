package interpreter.execution.service;

import interpreter.execution.handlers.InstructionHandler;
import interpreter.execution.model.ExecutionContext;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.Instruction;
import interpreter.translate.model.instruction.InstructionCode;

import java.util.Collection;

public class ExecutionService {

    private InstructionHandler[] instructionHandlers;
    private ExecutionContext executionContext;
    private InstructionPointer instructionPointer;

    public ExecutionService(){
        instructionHandlers = new InstructionHandler[InstructionCode.values().length];
    }

    public void addInstructions(Collection<? extends Instruction> instructions) {
        executionContext.addInstruction(instructions);
    }

    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
        this.instructionPointer = executionContext.newInstructionPointer();
    }

    public void registerInstructionHandler(InstructionCode instructionCode, InstructionHandler instructionHandler) {
        instructionHandlers[instructionCode.getIndex()] = instructionHandler;
        instructionHandler.setExecutionContext(executionContext);
    }



}
