package interpreter.execution.handlers;

import interpreter.execution.model.ExecutionContext;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.InstructionCode;

public abstract class AbstractInstructionHandler implements InstructionHandler {

    protected ExecutionContext executionContext;
    protected InstructionCode supportedInstructionCode;

    public abstract void handle(InstructionPointer instructionPointer);

    @Override
    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return supportedInstructionCode;
    }
}
