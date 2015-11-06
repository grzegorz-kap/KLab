package interpreter.execution.handlers;

import interpreter.commons.exception.InterpreterCastException;
import interpreter.execution.model.InstructionPointer;
import interpreter.service.functions.model.CallInstruction;
import interpreter.translate.model.FLNextInstruction;
import interpreter.translate.model.InstructionCode;
import interpreter.types.ObjectData;
import interpreter.types.foriterator.ForIterable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FLNextInstructionHandler extends AbstractInstructionHandler {
    @Override
    public void handle(InstructionPointer instructionPointer) {
        FLNextInstruction flInstruction = (FLNextInstruction) instructionPointer.current();
        ForIterable forIterable = getForIterable(executionContext.executionStackPop());

    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.FLNEXT;
    }

    private ForIterable getForIterable(ObjectData objectData) {
        if (objectData instanceof ForIterable) {
            return ((ForIterable) objectData);
        } else {
            throw new InterpreterCastException("Cannot cast to for-iterable");
        }
    }
}
