package com.klab.interpreter.service.functions.external;

import com.klab.interpreter.commons.MemorySpace;
import com.klab.interpreter.execution.handlers.AbstractInstructionHandler;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.ObjectData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FunctionEndInstructionHandler extends AbstractInstructionHandler {
    private MemorySpace memorySpace;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        EndFunctionInstruction instruction = (EndFunctionInstruction) instructionPointer.currentInstruction();
        List<ObjectData> output = new ArrayList<>(instruction.getOutputStart());
        final int end = instruction.getOutputStart() + instruction.getExpectedOutput();
        for (int index = end - 1; index >= instruction.getOutputStart(); index--) {
            ObjectData object = memorySpace.get(index);
            if (object == null) {
                throw new RuntimeException(); // TODO
            }
            output.add(object);
        }
        memorySpace.previousScope();
        executionContext.restoreExecutionStackSize();
        instructionPointer.restorePreviousCode();
        for (ObjectData objectData : output) {
            executionContext.executionStackPush(objectData);
        }
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.FUNCTION_END;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
