package interpreter.execution.handlers;

import interpreter.execution.model.ExecutionContext;
import interpreter.execution.model.InstructionPointer;

public interface InstructionHandler {
    void handle(InstructionPointer instructionPointer);

    void setExecutionContext(ExecutionContext executionContext);
}
