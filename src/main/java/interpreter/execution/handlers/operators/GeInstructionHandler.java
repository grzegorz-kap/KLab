package interpreter.execution.handlers.operators;

import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.instruction.InstructionCode;
import interpreter.types.ObjectData;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GeInstructionHandler extends AbstractOperatorInstructionHandler {

    @Override
    public void handle(InstructionPointer instructionPointer) {
        handleTwoArguments(instructionPointer);
    }

    @Override
    protected ObjectData calculate(ObjectData a, ObjectData b) {
        return operatorExecutionFactory.getComporator(numberType(a, b)).ge(a, b);
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.GE;
    }
}
