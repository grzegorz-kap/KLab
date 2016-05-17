package com.klab.interpreter.execution.service;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.klab.common.EventService;
import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.core.events.ReleaseBreakpointsEvent;
import com.klab.interpreter.core.events.StopExecutionEvent;
import com.klab.interpreter.debug.*;
import com.klab.interpreter.execution.InstructionAction;
import com.klab.interpreter.execution.handlers.InstructionHandler;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.profiling.ProfilingServiceImpl;
import com.klab.interpreter.translate.model.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExecutionServiceImpl extends AbstractExecutionService {
    private IdentifierMapper identifierMapper;
    private MemorySpace memorySpace;
    private InstructionAction handleAction = InstructionHandler::handle;
    private ProfilingServiceImpl profilingService;
    private BreakpointService breakpointService;
    private EventService eventService;
    private boolean stop = false;
    private ExecutionStep previousBreakpoint = null;

    @Override
    public void enableProfiling() {
        handleAction = profilingService;
    }

    @Override
    public void disableProfiling() {
        handleAction = InstructionHandler::handle;
    }

    @Subscribe
    public void onBreakpointsUpdated(BreakpointUpdatedEvent event) {
        ip.codeStream().forEach(breakpointService::updateBreakpoints);
        breakpointService.updateBreakpoints(ip.getCode());
    }

    @Override
    public void start() {
        memorySpace.reserve(identifierMapper.mainMappingsSize());
        stop = false;
        while (!ip.isCodeEnd() && !this.stop) {
            Instruction instruction = ip.currentInstruction();
            InstructionHandler instructionHandler = instructionHandlers[instruction.getInstructionCode().getIndex()];
            if (instruction.isBreakpoint()) {
                previousBreakpoint = null;
                breakpointService.block(instruction.getBreakpoint());
            } else if (previousBreakpoint != null && instruction.getCodeAddress() != null) {
                int line = instruction.getCodeAddress().getLine();
                if (previousBreakpoint.isStepInto() && previousBreakpoint.getCallLevel() < ip.callLevel()) {
                    block(instruction);
                } else if (ip.callLevel() == previousBreakpoint.getCallLevel() && previousBreakpoint.getLine() != line ||
                        ip.callLevel() < previousBreakpoint.getCallLevel()) {
                    block(instruction);
                }
            }
            handleAction.handle(instructionHandler, ip);
        }
    }

    private void block(Instruction instruction) {
        previousBreakpoint = null;
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

    @Subscribe
    private void onExecStop(StopExecutionEvent event) {
        this.stop = true;
        eventService.publish(new ReleaseBreakpointsEvent(this));
    }

    @Subscribe
    private void onStepOverEvent(StepOverEvent event) {
        previousBreakpoint = new ExecutionStep(event.getData().getLine(), ip.callLevel(), false);
    }

    @Subscribe
    private void onStepIntoEvent(StepIntoEvent event) {
        previousBreakpoint = new ExecutionStep(event.getData().getLine(), ip.callLevel(), true);
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

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
