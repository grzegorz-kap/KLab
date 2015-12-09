package interpreter.service.functions;

import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.ExecutionContext;
import interpreter.execution.model.InstructionPointer;
import interpreter.service.functions.exception.UndefinedFunctionException;
import interpreter.service.functions.model.CallInstruction;
import interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallInstructionHandler extends AbstractInstructionHandler {
    private InternalFunctionCallHandler internalFunctionCallHandler;
    private VariableFunctionCallHandler variableFunctionCallHandler;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        CallInstruction instruction = (CallInstruction) instructionPointer.currentInstruction();
        if (nonNull(instruction.getVariableAddress())) {
            variableFunctionCallHandler.handle(instructionPointer);
        } else if (nonNull(instruction.getInternalFunctionAddress())) {
            internalFunctionCallHandler.handle(instructionPointer);
        } else {
            throw new UndefinedFunctionException(instruction);
        }
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return InstructionCode.CALL;
    }

    @Override
    public void setExecutionContext(ExecutionContext executionContext) {
        super.setExecutionContext(executionContext);
        internalFunctionCallHandler.setExecutionContext(executionContext);
        variableFunctionCallHandler.setExecutionContext(executionContext);
    }

    @Autowired
    public void setInternalFunctionCallHandler(InternalFunctionCallHandler internalFunctionCallHandler) {
        this.internalFunctionCallHandler = internalFunctionCallHandler;
    }

    @Autowired
    public void setVariableFunctionCallHandler(VariableFunctionCallHandler variableFunctionCallHandler) {
        this.variableFunctionCallHandler = variableFunctionCallHandler;
    }
}
