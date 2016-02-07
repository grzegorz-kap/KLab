package interpreter.execution.service;

import interpreter.commons.IdentifierMapper;
import interpreter.commons.MemorySpace;
import interpreter.execution.InstructionAction;
import interpreter.execution.exception.UnsupportedInstructionException;
import interpreter.execution.handlers.InstructionHandler;
import interpreter.execution.model.InstructionPointer;
import interpreter.profiling.ProfilingService;
import interpreter.translate.model.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExecutionServiceImpl extends AbstractExecutionService {
    public static final String UNEXPECTED_INSTRUCTION_MESSAGE = "Unexpected instruction";
    private IdentifierMapper identifierMapper;
    private MemorySpace memorySpace;
    private InstructionAction handleAction = this::handle;
    private ProfilingService profilingService;

    @Autowired
    public ExecutionServiceImpl(Set<InstructionHandler> instructionHandlers) {
        super(instructionHandlers);
    }

    @Override
    public void enableProfiling() {
        handleAction = profilingService;
    }

    @Override
    public void disableProfiling() {
        handleAction = this::handle;
    }

    @Override
    public void start() {
        memorySpace.reserve(identifierMapper.mainMappingsSize());
        while (!instructionPointer.isCodeEnd()) {
            Instruction instruction = instructionPointer.currentInstruction();
            InstructionHandler instructionHandler = instructionHandlers[instruction.getInstructionCode().getIndex()];
            if (instructionHandler == null) {
                throw new UnsupportedInstructionException(UNEXPECTED_INSTRUCTION_MESSAGE, instruction);
            }
            handleAction.handle(instructionHandler, instructionPointer);
        }
    }

    private void handle(InstructionHandler handler, InstructionPointer pointer) {
        handler.handle(pointer);
    }

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    @Autowired
    public void setProfilingService(ProfilingService profilingService) {
        this.profilingService = profilingService;
    }
}
