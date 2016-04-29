package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.translate.model.JumperInstruction;
import com.klab.interpreter.types.ObjectData;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMPFInstructionHandler extends AbstractInstructionHandler {

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.JMPF;
    }

    @Override
    public void handle(InstructionPointer instructionPointer) {
        ObjectData objectData = executionContext.executionStackPop();
        if (!objectData.isTrue()) {
            JumperInstruction jumperInstruction = (JumperInstruction) instructionPointer.currentInstruction();
            instructionPointer.jumpTo(jumperInstruction.getJumpIndex());
        } else {
            instructionPointer.increment();
        }
    }
}
