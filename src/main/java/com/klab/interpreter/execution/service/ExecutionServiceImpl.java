package com.klab.interpreter.execution.service;

import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.debug.Breakpoint;
import com.klab.interpreter.debug.BreakpointService;
import com.klab.interpreter.execution.InstructionAction;
import com.klab.interpreter.execution.exception.UnsupportedInstructionException;
import com.klab.interpreter.execution.handlers.InstructionHandler;
import com.klab.interpreter.profiling.ProfilingServiceImpl;
import com.klab.interpreter.translate.model.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExecutionServiceImpl extends AbstractExecutionService {
    private static final String UNEXPECTED_INSTRUCTION_MESSAGE = "Unexpected instruction";
    private IdentifierMapper identifierMapper;
    private MemorySpace memorySpace;
    private InstructionAction handleAction = InstructionHandler::handle;
    private ProfilingServiceImpl profilingService;
    private BreakpointService breakpointService;

    @Override
    public void enableProfiling() {
        handleAction = profilingService;
    }

    @Override
    public void disableProfiling() {
        handleAction = InstructionHandler::handle;
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
                Breakpoint breakpoint = new Breakpoint(instructionPointer.getSourceId(), instruction.getCodeAddress().getLine());
                breakpointService.block(breakpoint);
            }
            handleAction.handle(instructionHandler, instructionPointer);
        }
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
    public void setProfilingService(ProfilingServiceImpl profilingService) {
        this.profilingService = profilingService;
    }

    @Autowired
    public void setBreakpointService(BreakpointService breakpointService) {
        this.breakpointService = breakpointService;
    }
}
