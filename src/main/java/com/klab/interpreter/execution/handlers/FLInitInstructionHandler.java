package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.commons.exception.InterpreterCastException;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.IdentifierObject;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.foriterator.ForIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FLInitInstructionHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        ForIterable forIterable = getForIterable(executionContext.executionStackPop());
        IdentifierObject identifierObject = (IdentifierObject) instructionPointer.currentInstruction().getObjectData(0);
        memorySpace.set(identifierObject.getAddress(), forIterable.getForIterator(), identifierObject.getId());
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.FLINIT;
    }

    private ForIterable getForIterable(ObjectData objectData) {
        if (objectData instanceof ForIterable) {
            return ((ForIterable) objectData);
        } else {
            throw new InterpreterCastException("Cannot cast to for-iterable");
        }
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
