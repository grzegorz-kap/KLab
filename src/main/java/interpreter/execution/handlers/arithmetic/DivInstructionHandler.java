package interpreter.execution.handlers.arithmetic;

import interpreter.commons.ObjectData;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.InstructionCode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DivInstructionHandler extends AbstractArithmeticInstructionHandler {

    @Override
    public void handle(InstructionPointer instructionPointer) {
        handleTwoArguments(instructionPointer);
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.DIV;
    }

    @Override
    public ObjectData calculate(ObjectData a, ObjectData b) {
        return arithmeticOperationsFactory.getDivider(numberType(a, b)).div(a, b);
    }
}
