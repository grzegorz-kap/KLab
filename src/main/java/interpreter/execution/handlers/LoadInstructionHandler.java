package interpreter.execution.handlers;

import interpreter.commons.MemorySpace;
import interpreter.execution.exception.UndefinedVariableException;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.IdentifierObject;
import interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoadInstructionHandler extends AbstractInstructionHandler {

    public static final String VARIABLE_IS_NOT_DEFINED_MESSAGE = "Variable is not defined";
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        IdentifierObject identifierObject = (IdentifierObject) instructionPointer.current().getObjectData(0);
        ObjectData objectData = memorySpace.get(identifierObject.getAddress());
        checkObjectData(objectData, identifierObject);
        executionContext.executionStackPush(objectData);
        instructionPointer.increment();
    }

    private void checkObjectData(ObjectData objectData, IdentifierObject identifierObject) {
        if (Objects.isNull(objectData)) {
            throw new UndefinedVariableException(VARIABLE_IS_NOT_DEFINED_MESSAGE, identifierObject);
        }
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
