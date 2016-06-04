package com.klab.interpreter.service.functions;

import com.klab.interpreter.execution.handlers.AbstractInstructionHandler;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.service.functions.model.CallInstruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.MultiOutput;
import com.klab.interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
            MultiOutput outData = (MultiOutput) result;
            for (int i = outData.size() - 1; i >= 0; i--) {
                executionContext.executionStackPush(Objects.requireNonNull(outData.get(i)));
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
