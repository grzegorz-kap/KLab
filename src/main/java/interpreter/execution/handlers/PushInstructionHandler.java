package interpreter.execution.handlers;

import interpreter.commons.ObjectData;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.Instruction;

public class PushInstructionHandler extends AbstractInstructionHandler {

    @Override
    public void handle(InstructionPointer instructionPointer) {
        Instruction instruction = instructionPointer.current();
        ObjectData objectData = instruction.getObjectDate(0);
        executionContext.pushToExecutionStack(objectData);
        instructionPointer.increment();
    }
}
