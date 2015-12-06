package interpreter.execution.handlers.operators;

import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.NumericObject;
import interpreter.types.NumericType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AddInstructionHandler extends AbstractOperatorInstructionHandler {

    @Override
    public void handle(InstructionPointer instructionPointer) {
        handleTwoArguments(instructionPointer);
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.ADD;
    }

    @Override
    public NumericObject calculate(NumericObject a, NumericObject b, NumericType type) {
        return operatorFactory.getOperator(type).add(convert(a, type), convert(b, type));
    }
}
