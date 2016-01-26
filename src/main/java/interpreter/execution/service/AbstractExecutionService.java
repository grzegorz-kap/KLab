package interpreter.execution.service;

import interpreter.execution.handlers.InstructionHandler;
import interpreter.execution.model.ExecutionContext;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;

import java.util.Set;

import static java.util.Objects.nonNull;

public abstract class AbstractExecutionService {
    protected InstructionHandler[] instructionHandlers = new InstructionHandler[InstructionCode.values().length];
    protected ExecutionContext executionContext;
    protected InstructionPointer instructionPointer;

    public AbstractExecutionService(Set<InstructionHandler> instructionHandlers) {
        this.executionContext = new ExecutionContext();
        this.instructionPointer = this.executionContext.newInstructionPointer();
        instructionHandlers.stream()
                .filter(instructionHandler -> nonNull(instructionHandler.getSupportedInstructionCode()))
                .forEach( handler -> {
                    InstructionCode code = handler.getSupportedInstructionCode();
                    this.instructionHandlers[code.getIndex()] = handler;
                    handler.setExecutionContext(this.executionContext);
                });
    }

    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    public void resetCodeAndStack() {
        executionContext.clearExecutionStack();
        executionContext.clearCode();
        instructionPointer = executionContext.newInstructionPointer();
    }
}
