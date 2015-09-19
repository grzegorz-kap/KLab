package interpreter.execution.handlers.arithmetic;

import interpreter.commons.ObjectData;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.InstructionCode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AddInstructionHandler extends AbstractArithmeticInstructionHandler {

    @PostConstruct
    private void init() {
        supportedInstructionCode = InstructionCode.ADD;
    }

    @Override
    public void handle(InstructionPointer instructionPointer) {
        handleTwoArguments(instructionPointer);
    }

    @Override
    public ObjectData calculate(ObjectData a, ObjectData b) {
        return arithmeticOperationsFactory.getAdder(numberType(a, b)).add(a, b);
    }
}
