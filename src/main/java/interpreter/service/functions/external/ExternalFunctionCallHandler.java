package interpreter.service.functions.external;

import interpreter.execution.handlers.AbstractInstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.service.functions.model.CallInstruction;
import interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExternalFunctionCallHandler extends AbstractInstructionHandler {
    private ExternalFunctionService externalFunctionService;

    @Override
    public void handle(InstructionPointer instructionPointer) {
        CallInstruction cI = (CallInstruction) instructionPointer.currentInstruction();
        ExternalFunction extFunction = externalFunctionService.loadFunction(cI.getExternalFunctionAddress(), cI.getName());
        throw new UnsupportedOperationException();
        //TODO make supported
    }

    @Override
    public InstructionCode getSupportedInstructionCode() {
        return null;
    }

    @Autowired
    public void setExternalFunctionService(ExternalFunctionService externalFunctionService) {
        this.externalFunctionService = externalFunctionService;
    }
}
