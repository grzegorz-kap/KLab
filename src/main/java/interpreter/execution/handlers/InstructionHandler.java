package interpreter.execution.handlers;

import interpreter.execution.model.ExecutionContext;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.InstructionCode;

public interface InstructionHandler {
    void handle(InstructionPointer instructionPointer);

    void setExecutionContext(ExecutionContext executionContext);

    InstructionCode getSupportedInstructionCode();
}
