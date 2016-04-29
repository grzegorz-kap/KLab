package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.translate.model.JumperInstruction;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMPInstructionHandler extends AbstractInstructionHandler {

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.JMP;
    }

    @Override
    public void handle(InstructionPointer instructionPointer) {
        JumperInstruction jumperInstruction = (JumperInstruction) instructionPointer.currentInstruction();
        instructionPointer.jumpTo(jumperInstruction.getJumpIndex());
    }
}
