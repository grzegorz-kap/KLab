package interpreter.execution.handlers.operators;

import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.InstructionCode;
import interpreter.types.ObjectData;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DivInstructionHandler extends AbstractOperatorInstructionHandler {

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
        return operatorExecutionFactory.getDivider(numberType(a, b)).div(a, b);
    }
}
