package interpreter.execution.service;

import common.EventService;
import interpreter.commons.IdentifierMapper;
import interpreter.commons.MemorySpace;
import interpreter.debug.Breakpoint;
import interpreter.debug.BreakpointReachedEvent;
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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExecutionServiceImpl extends AbstractExecutionService {
    private static final String UNEXPECTED_INSTRUCTION_MESSAGE = "Unexpected instruction";
    private IdentifierMapper identifierMapper;
    private MemorySpace memorySpace;
    private InstructionAction handleAction = this::handle;
    private ProfilingService profilingService;
    private EventService eventService;

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
            if (instruction.isBreakpoint()) {
                Lock lock = new ReentrantLock();
                Condition released = lock.newCondition();
                try {
                    Breakpoint breakpoint = new Breakpoint(executionContext.getCode().getSourceId(), instruction.getCodeAddress().getLine());
                    eventService.publish(new BreakpointReachedEvent(breakpoint, this, lock, released));
                    lock.lock();
                    while (!breakpoint.isReleased())
                        released.await();
                } catch (Exception ex) {
                    lock.unlock();
                }
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

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
