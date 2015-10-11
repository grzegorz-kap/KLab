package interpreter.execution.service;

import interpreter.execution.exception.UnsupportedInstructionException;
import interpreter.execution.handlers.InstructionHandler;
import interpreter.translate.model.instruction.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExecutionService extends AbstractExecutionService {

    public static final String UNEXPECTED_INSTRUCTION_MESSAGE = "Unexpected instruction";

    @Autowired
    public ExecutionService(Set<InstructionHandler> instructionHandlers) {
        super(instructionHandlers);
    }

    public void start() {
        while (!instructionPointer.isCodeEnd()) {
            Instruction instruction = instructionPointer.current();
            InstructionHandler instructionHandler = instructionHandlers[instruction.getInstructionCode().getIndex()];
            checkChandler(instruction, instructionHandler);
            instructionHandler.handle(instructionPointer);
        }
    }

    private void checkChandler(Instruction instruction, InstructionHandler instructionHandler) {
        if (Objects.isNull(instructionHandler)) {
            throw new UnsupportedInstructionException(UNEXPECTED_INSTRUCTION_MESSAGE, instruction);
        }
    }
}
