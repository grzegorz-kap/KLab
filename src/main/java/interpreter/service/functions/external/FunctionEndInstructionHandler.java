package interpreter.service.functions.external;

import interpreter.commons.MemorySpace;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.service.functions.model.CallInstruction;
import interpreter.translate.model.InstructionCode;
import interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FunctionEndInstructionHandler extends AbstractInstructionHandler {
    @Autowired
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        EndFunctionInstruction instruction = (EndFunctionInstruction) instructionPointer.currentInstruction();
        ObjectData objectData = memorySpace.get(instruction.getOutputStart());
        memorySpace.previousScope();
        executionContext.restoreExecutionStackSize();
        instructionPointer.restorePreviousCode();
        executionContext.executionStackPush(objectData);
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.FUNCTION_END;
    }
}
