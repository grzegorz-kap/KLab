package interpreter.execution.handlers.indexing;

import interpreter.commons.MemorySpace;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.IdentifierObject;
import interpreter.types.RangeAddressIterator;
import interpreter.types.Sizeable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MAllRowsInstructionHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        IdentifierObject id = (IdentifierObject) instructionPointer.currentInstruction().getObjectData(0);
        Sizeable sizeable = (Sizeable) memorySpace.get(id.getAddress());
        executionContext.executionStackPush(new RangeAddressIterator(1, sizeable.getRows()));
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.MALLROWS;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
