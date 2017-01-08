package com.klab.interpreter.execution.handlers;

import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ScriptExitHandler extends AbstractInstructionHandler {
    @Override
    public void handle(InstructionPointer instructionPointer) {
        instructionPointer.restorePreviousCode();
        while (!instructionPointer.isCodeEnd()) {
            InstructionCode instructionCode = instructionPointer.currentInstruction().getInstructionCode();
            if (!InstructionCode.ANS.equals(instructionCode) && !InstructionCode.PRINT.equals(instructionCode)) {
                break;
            } else {
                instructionPointer.increment();
            }
        }
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.SCRIPT_EXIT;
    }
}
