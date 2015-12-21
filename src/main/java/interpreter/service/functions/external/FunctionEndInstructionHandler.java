package interpreter.service.functions.external;

import interpreter.commons.MemorySpace;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
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
        memorySpace.previousScope();
        executionContext.restoreExecutionStackSize();
        instructionPointer.restorePreviousCode();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.FUNCTION_END;
    }
}
