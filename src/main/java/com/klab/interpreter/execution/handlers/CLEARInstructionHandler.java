package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.IdentifierObject;
import com.klab.interpreter.types.TokenIdentifierObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CLEARInstructionHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        IdentifierObject id = instructionPointer.currentInstruction().getData(0, TokenIdentifierObject.class);
        memorySpace.set(id.getAddress(), null, null);
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.CLEAR;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
