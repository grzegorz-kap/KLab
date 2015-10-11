package interpreter.execution.handlers;

import interpreter.commons.MemorySpace;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.InstructionCode;
import interpreter.types.IdentifierObject;
import interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoadInstructionHandler extends AbstractInstructionHandler {

    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        IdentifierObject identifierObject = (IdentifierObject) instructionPointer.current().getObjectDate(0);
        ObjectData objectData = memorySpace.get(identifierObject.getAddress());
        executionContext.executionStackPush(objectData);
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.LOAD;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
