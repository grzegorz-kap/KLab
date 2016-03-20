package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.commons.MemorySpace;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.FLNextInstruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.foriterator.ForIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FLNextInstructionHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        FLNextInstruction flInstruction = (FLNextInstruction) instructionPointer.currentInstruction();
        ForIterator forIterator = (ForIterator) memorySpace.get(flInstruction.getIteratorData().getAddress());
        if (forIterator.hasNext()) {
            memorySpace.set(flInstruction.getIteratorId().getAddress(), forIterator.getNext(), flInstruction.getIteratorId().getId());
            instructionPointer.increment();
        } else {
            instructionPointer.jumpTo(flInstruction.getJumpIndex());
        }
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.FLNEXT;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
