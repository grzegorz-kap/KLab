package com.klab.interpreter.service.functions;

import com.klab.interpreter.commons.MemorySpace;
import com.klab.interpreter.execution.handlers.AbstractInstructionHandler;
import com.klab.interpreter.execution.model.ExecutionContext;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.service.functions.external.ExternalFunctionCallHandler;
import com.klab.interpreter.service.functions.model.CallInstruction;
import com.klab.interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallInstructionHandler extends AbstractInstructionHandler {
    private InternalFunctionCallHandler internalFunctionCallHandler;
    private VariableFunctionCallHandler variableFunctionCallHandler;
    private ExternalFunctionCallHandler externalFunctionCallHandler;
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        CallInstruction instruction = (CallInstruction) instructionPointer.currentInstruction();
        Integer varPtr = instruction.getVariableAddress();
        if (varPtr != null && memorySpace.get(varPtr) != null) {
            variableFunctionCallHandler.handle(instructionPointer);
        } else if (instruction.getInternalFunctionAddress() != null) {
            internalFunctionCallHandler.handle(instructionPointer);
        } else {
            externalFunctionCallHandler.handle(instructionPointer);
        }
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.CALL;
    }

    @Override
    public void setExecutionContext(ExecutionContext executionContext) {
        super.setExecutionContext(executionContext);
        internalFunctionCallHandler.setExecutionContext(executionContext);
        variableFunctionCallHandler.setExecutionContext(executionContext);
        externalFunctionCallHandler.setExecutionContext(executionContext);
    }

    @Autowired
    public void setInternalFunctionCallHandler(InternalFunctionCallHandler internalFunctionCallHandler) {
        this.internalFunctionCallHandler = internalFunctionCallHandler;
    }

    @Autowired
    public void setVariableFunctionCallHandler(VariableFunctionCallHandler variableFunctionCallHandler) {
        this.variableFunctionCallHandler = variableFunctionCallHandler;
    }

    @Autowired
    public void setExternalFunctionCallHandler(ExternalFunctionCallHandler externalFunctionCallHandler) {
        this.externalFunctionCallHandler = externalFunctionCallHandler;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
