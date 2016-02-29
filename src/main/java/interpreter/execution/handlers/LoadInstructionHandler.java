package interpreter.execution.handlers;

import interpreter.commons.MemorySpace;
import interpreter.core.code.ScriptService;
import interpreter.execution.exception.UndefinedVariableException;
import interpreter.execution.model.Code;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.IdentifierObject;
import interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoadInstructionHandler extends AbstractInstructionHandler {
    public static final String VARIABLE_IS_NOT_DEFINED_MESSAGE = "Variable is not defined";
    private MemorySpace memorySpace;
    private ScriptService scriptService;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        IdentifierObject identifierObject = (IdentifierObject) instructionPointer.currentInstruction().getObjectData(0);
        handle(identifierObject, instructionPointer);
    }

    private void handle(IdentifierObject identifierObject, InstructionPointer instructionPointer) {
        ObjectData objectData = memorySpace.get(identifierObject.getAddress());
        instructionPointer.increment();
        if (objectData == null) {
            Code code;
            if (identifierObject.isCanBeScript() && (code = scriptService.getCode(identifierObject.getId())) != null) {
                instructionPointer.moveToCode(code);
            } else {
                throw new UndefinedVariableException(VARIABLE_IS_NOT_DEFINED_MESSAGE, identifierObject);
            }
        } else {
            executionContext.executionStackPush(objectData);
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

    @Autowired
    public void setScriptService(ScriptService scriptService) {
        this.scriptService = scriptService;
    }
}
