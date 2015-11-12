package interpreter.service.functions;

import interpreter.commons.MemorySpace;
import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.service.functions.model.CallInstruction;
import interpreter.translate.model.InstructionCode;
import interpreter.types.Indexable;
import interpreter.types.ObjectData;
import interpreter.types.scalar.Scalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VariableFunctionCallHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        CallInstruction callInstruction = (CallInstruction) instructionPointer.current();
        Indexable indexable = (Indexable) memorySpace.get(callInstruction.getVariableAddress());
        if (callInstruction.getArgumentsNumber() == 2) {
            handleTwo(indexable);
        }
        instructionPointer.increment();
    }

    private void handleTwo(Indexable indexable) {
        int column = ((Scalar) executionContext.executionStackPop()).getValue().intValue();
        int row = ((Scalar) executionContext.executionStackPop()).getValue().intValue();
        ObjectData objectData = indexable.get(row, column);
        executionContext.executionStackPush(objectData);
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return null;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
