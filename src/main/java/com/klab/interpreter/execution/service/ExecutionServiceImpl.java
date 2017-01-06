package com.klab.interpreter.execution.service;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.klab.common.EventService;
import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.core.ExecutionError;
import com.klab.interpreter.core.events.ReleaseBreakpointsEvent;
import com.klab.interpreter.core.events.StopExecutionEvent;
import com.klab.interpreter.debug.*;
import com.klab.interpreter.execution.InstructionAction;
import com.klab.interpreter.execution.handlers.InstructionHandler;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.execution.model.ExecutionContext;
import com.klab.interpreter.execution.model.InstructionPointer;
import com.klab.interpreter.profiling.ProfilingServiceImpl;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.InstructionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExecutionServiceImpl implements ExecutionService {
    private IdentifierMapper identifierMapper;
    private MemorySpace memorySpace;
    private InstructionAction handleAction = InstructionHandler::handle;
    private ProfilingServiceImpl profilingService;
    private BreakpointService breakpointService;
    private EventService eventService;
    private PauseStep executionPause = new NoPauseStep();
    private boolean stop = false;
    private ExecutionContext executionContext;
    private InstructionHandler[] handlers = new InstructionHandler[InstructionCode.values().length];
    private InstructionPointer ip;

    @Override
    public void start() throws ExecutionError {
        memorySpace.reserve(identifierMapper.mainMappingsSize());
        stop = false;
        while (!ip.isCodeEnd() && !this.stop) {
            Instruction instr = ip.currentInstruction();
            if (instr.isBreakpoint()) {
                executionPause = new NoPauseStep();
                breakpointService.block(instr.getBreakpoint());
            } else if (executionPause.shouldStop(ip)) {
                block(instr);
            }
            InstructionHandler handler = handlers[instr.handlerIndex()];
            try {
                handleAction.handle(handler, ip);
            } catch (Exception ex) {
                throw new ExecutionError(ex.getMessage(), ip.getSourceId(), ex, instr);
            }
        }
    }

    @Override
    public void enableProfiling() {
        handleAction = profilingService;
    }

    @Override
    public void disableProfiling() {
        handleAction = InstructionHandler::handle;
    }

    private void block(Instruction instruction) {
        executionPause = new NoPauseStep();
        BreakpointImpl breakpoint = new BreakpointImpl(ip.getSourceId(), instruction.getCodeAddress().getLine(), instruction);
        breakpoint.setCode(ip.getCode());
        breakpointService.block(breakpoint);
    }

    @Override
    public List<Code> callStack() {
        List<Code> callStack = Lists.newArrayList();
        callStack.add(ip.getCode());
        Iterator<Code> iterator = ip.stackIterator();
        Code code;
        while (iterator.hasNext() && (code = iterator.next()) != null) {
            callStack.add(code);
        }
        return callStack;
    }

    @Override
    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    @Override
    public void resetCodeAndStack() {
        executionContext.clearExecutionStack();
        executionContext.clearCode();
        ip = executionContext.newInstructionPointer();
    }

    @Subscribe
    private void onRunToEvent(RunToEvent event) {
        executionPause = new RunTo(event.getLine(), event.getScript());
    }

    @Subscribe
    private void onBreakpointsUpdated(BreakpointUpdatedEvent event) {
        ip.codeStream().forEach(breakpointService::updateBreakpoints);
        breakpointService.updateBreakpoints(ip.getCode());
    }

    @Subscribe
    private void onExecStop(StopExecutionEvent event) {
        this.stop = true;
        eventService.publish(new ReleaseBreakpointsEvent(this));
    }

    @Subscribe
    private void onStepOverEvent(StepOverEvent event) {
        executionPause = new Stepover(ip.callLevel(), event.getData().getLine());
    }

    @Subscribe
    private void onStepIntoEvent(StepIntoEvent event) {
        executionPause = new StepInto(ip.callLevel(), event.getData().getLine());
    }

    @Autowired
    private void setHandlers(Collection<InstructionHandler> handlers) {
        this.executionContext = new ExecutionContext();
        this.ip = this.executionContext.newInstructionPointer();
        handlers.stream()
                .filter(instructionHandler -> nonNull(instructionHandler.getSupportedInstructionCode()))
                .forEach(handler -> {
                    InstructionCode code = handler.getSupportedInstructionCode();
                    this.handlers[code.getIndex()] = handler;
                    handler.setExecutionContext(this.executionContext);
                });
    }

    @Autowired
    void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }

    @Autowired
    void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    @Autowired
    void setProfilingService(ProfilingServiceImpl profilingService) {
        this.profilingService = profilingService;
    }

    @Autowired
    void setBreakpointService(BreakpointService breakpointService) {
        this.breakpointService = breakpointService;
    }

    @Autowired
    void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
