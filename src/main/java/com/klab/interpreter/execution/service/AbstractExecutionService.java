package com.klab.interpreter.execution.service;

import com.klab.interpreter.execution.handlers.InstructionHandler;
import com.klab.interpreter.execution.model.ExecutionContext;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;

import java.util.Set;

import static java.util.Objects.nonNull;

public abstract class AbstractExecutionService implements ExecutionService {
    protected InstructionHandler[] instructionHandlers = new InstructionHandler[InstructionCode.values().length];
    protected ExecutionContext executionContext;
    protected InstructionPointer instructionPointer;

    public AbstractExecutionService(Set<InstructionHandler> instructionHandlers) {
        this.executionContext = new ExecutionContext();
        this.instructionPointer = this.executionContext.newInstructionPointer();
        instructionHandlers.stream()
                .filter(instructionHandler -> nonNull(instructionHandler.getSupportedInstructionCode()))
                .forEach(handler -> {
                    InstructionCode code = handler.getSupportedInstructionCode();
                    this.instructionHandlers[code.getIndex()] = handler;
                    handler.setExecutionContext(this.executionContext);
                });
    }

    @Override
    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    @Override
    public void resetCodeAndStack() {
        executionContext.clearExecutionStack();
        executionContext.clearCode();
        instructionPointer = executionContext.newInstructionPointer();
    }
}
