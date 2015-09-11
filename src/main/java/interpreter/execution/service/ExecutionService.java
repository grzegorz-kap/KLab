package interpreter.execution.service;

import interpreter.execution.handlers.InstructionHandler;
import interpreter.execution.model.ExecutionContext;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.Instruction;
import interpreter.translate.model.instruction.InstructionCode;

import java.util.Collection;
import java.util.Objects;

public class ExecutionService {

    private InstructionHandler[] instructionHandlers;
    private ExecutionContext executionContext;
    private InstructionPointer instructionPointer;

    public ExecutionService(){
        instructionHandlers = new InstructionHandler[InstructionCode.values().length];
    }

    public void start() {
        while (!instructionPointer.isCodeEnd()) {
            Instruction instruction = instructionPointer.current();
            InstructionHandler instructionHandler = instructionHandlers[instruction.getInstructionCode().getIndex()];
            if(Objects.isNull(instructionHandler)) {
                System.out.println(instruction.getInstructionCode());
                instructionPointer.increment();
            } else {
                instructionHandler.handle(instructionPointer);
            }
        }
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

    public void resetCodeAndStack() {
        executionContext.clearExecutionStack();
        executionContext.clearCode();
        instructionPointer = executionContext.newInstructionPointer();
    }
}
