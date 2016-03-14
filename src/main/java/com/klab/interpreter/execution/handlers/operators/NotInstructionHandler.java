package com.klab.interpreter.execution.handlers.operators;

import com.klab.interpreter.execution.handlers.AbstractInstructionHandler;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.translate.model.InstructionCode;
import com.klab.interpreter.types.Negable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NotInstructionHandler extends AbstractInstructionHandler {
    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.NOT;
    }

    @Override
    public void handle(InstructionPointer instructionPointer) {
        executionContext.executionStackPush(((Negable<?>) executionContext.executionStackPop()).negate());
        instructionPointer.increment();
    }
}
