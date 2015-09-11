package interpreter.execution.handlers;

import interpreter.execution.model.ExecutionContext;
import interpreter.execution.model.InstructionPointer;

public abstract class AbstractInstructionHandler implements InstructionHandler {

    protected ExecutionContext executionContext;

    public abstract void handle(InstructionPointer instructionPointer);

    @Override
    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }
}
