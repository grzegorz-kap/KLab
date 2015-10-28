package interpreter.execution.handlers;

import interpreter.core.internal.function.InternalFunction;
import interpreter.core.internal.function.InternalFunctionsHolder;
import interpreter.execution.model.InstructionPointer;
import interpreter.translate.model.CallInstruction;
import interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallInstructionHandler extends AbstractInstructionHandler {

    private InternalFunctionsHolder internalFunctionsHolder;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        CallInstruction instruction = (CallInstruction) instructionPointer.current();
        InternalFunction internalFunction = internalFunctionsHolder.get(instruction.getVariableAddress());
        throw new RuntimeException();
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.CALL;
    }

    @Autowired
    public void setInternalFunctionsHolder(InternalFunctionsHolder internalFunctionsHolder) {
        this.internalFunctionsHolder = internalFunctionsHolder;
    }
}
