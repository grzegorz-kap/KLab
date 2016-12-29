package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.ObjectData;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PushInstructionHandler extends AbstractInstructionHandler {
    @Override
    public void handle(InstructionPointer instructionPointer) {
        Instruction instruction = instructionPointer.currentInstruction();
        ObjectData objectData = instruction.getData(0);
        executionContext.executionStackPush(objectData);
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.PUSH;
    }
}
