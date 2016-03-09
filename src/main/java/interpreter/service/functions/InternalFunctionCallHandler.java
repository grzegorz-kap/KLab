package interpreter.service.functions;

import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.service.functions.model.CallInstruction;
import interpreter.translate.model.InstructionCode;
import interpreter.types.MultiOutput;
import interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InternalFunctionCallHandler extends AbstractInstructionHandler {
    private InternalFunctionsHolder internalFunctionsHolder;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        CallInstruction instruction = (CallInstruction) instructionPointer.currentInstruction();
        InternalFunction internalFunction = internalFunctionsHolder.get(instruction.getInternalFunctionAddress());
        ObjectData[] data = new ObjectData[instruction.getArgumentsNumber()];
        for (int index = instruction.getArgumentsNumber() - 1; index >= 0; index--) {
            data[index] = executionContext.executionStackPop();
        }
        ObjectData result = internalFunction.call(data, instruction.getExpectedOutputSize());

        if (instruction.getExpectedOutputSize() > 1 && !(result instanceof MultiOutput)) {
            throw new RuntimeException(); //TODO
        }
        if (instruction.getExpectedOutputSize() > 1) {
            for (ObjectData objectData : ((MultiOutput) result)) {
                executionContext.executionStackPush(objectData);
            }
        } else {
            executionContext.executionStackPush(result);
        }
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return null;
    }

    @Autowired
    public void setInternalFunctionsHolder(InternalFunctionsHolder internalFunctionsHolder) {
        this.internalFunctionsHolder = internalFunctionsHolder;
    }
}
