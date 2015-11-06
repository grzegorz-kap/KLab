package interpreter.execution.handlers;

import interpreter.commons.MemorySpace;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.FLNextInstruction;
import interpreter.translate.model.InstructionCode;
import interpreter.types.foriterator.ForIterator;
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
        FLNextInstruction flInstruction = (FLNextInstruction) instructionPointer.current();
        ForIterator forIterator = (ForIterator) memorySpace.get(flInstruction.getIteratorData().getAddress());
        if (forIterator.hasNext()) {
            memorySpace.set(flInstruction.getIteratorId().getAddress(), forIterator.getNext());
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
