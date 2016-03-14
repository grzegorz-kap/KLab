package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.commons.MemorySpace;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.IdentifierObject;
import com.klab.interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StoreInstructionHandler extends AbstractInstructionHandler {

    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        ObjectData source = executionContext.executionStackPop();
        IdentifierObject target = (IdentifierObject) executionContext.executionStackPop();
        source.setName(target.getId());
        memorySpace.set(target.getAddress(), source);
        executionContext.executionStackPush(source);
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.STORE;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
