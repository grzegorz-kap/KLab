package com.klab.interpreter.execution.service;

import com.klab.common.EventService;
import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.debug.Breakpoint;
import com.klab.interpreter.debug.BreakpointReachedEvent;
import com.klab.interpreter.execution.InstructionAction;
import com.klab.interpreter.execution.exception.UnsupportedInstructionException;
import com.klab.interpreter.execution.handlers.InstructionHandler;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.profiling.ProfilingService;
import com.klab.interpreter.translate.model.Instruction;
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
                hitBreakpoint(instruction);
            }
            handleAction.handle(instructionHandler, instructionPointer);
        }
    }

    private void hitBreakpoint(Instruction instruction) {
        Lock lock = new ReentrantLock();
        Condition released = lock.newCondition();
        Breakpoint breakpoint = new Breakpoint(instructionPointer.getSourceId(), instruction.getCodeAddress().getLine());
        eventService.publish(new BreakpointReachedEvent(breakpoint, this, lock, released));
        try {
            lock.lock();
            while (!breakpoint.isReleased())
                released.await();
        } catch (Exception ex) {
            lock.unlock();
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
