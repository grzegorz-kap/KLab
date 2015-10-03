package interpreter.execution.handlers;

import interpreter.commons.ObjectData;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.Instruction;
import interpreter.translate.model.instruction.InstructionCode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PushInstructionHandler extends AbstractInstructionHandler {

    @Override
    public void handle(InstructionPointer instructionPointer) {
        Instruction instruction = instructionPointer.current();
        ObjectData objectData = instruction.getObjectDate(0);
        executionContext.executionStackPush(objectData);
        instructionPointer.increment();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.PUSH;
    }
}
