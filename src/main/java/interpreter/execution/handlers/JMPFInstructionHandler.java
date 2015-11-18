package interpreter.execution.handlers;

import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.translate.model.JumperInstruction;
import interpreter.types.ObjectData;
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
            JumperInstruction jumperInstruction = (JumperInstruction) instructionPointer.current();
            instructionPointer.jumpTo(jumperInstruction.getJumpIndex());
        } else {
            instructionPointer.increment();
        }
    }
}
