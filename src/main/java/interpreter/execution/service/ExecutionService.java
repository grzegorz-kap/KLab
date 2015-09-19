package interpreter.execution.service;

import interpreter.execution.handlers.InstructionHandler;
import interpreter.translate.model.instruction.Instruction;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExecutionService extends AbstractExecutionService {

    public void start() {
        while (!instructionPointer.isCodeEnd()) {
            Instruction instruction = instructionPointer.current();
            InstructionHandler instructionHandler = instructionHandlers[instruction.getInstructionCode().getIndex()];
            instructionHandler.handle(instructionPointer);
        }
    }
}
