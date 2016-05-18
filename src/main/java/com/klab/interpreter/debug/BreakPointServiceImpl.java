package com.klab.interpreter.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.klab.common.EventService;
import com.klab.interpreter.core.Interpreter;
import com.klab.interpreter.core.events.ExecutionStartedEvent;
import com.klab.interpreter.core.events.ReleaseBreakpointsEvent;
import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.translate.model.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.MoreObjects.firstNonNull;
import static java.util.Collections.emptySet;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Service
public class BreakpointServiceImpl implements BreakpointService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BreakpointService.class);
    private EventService eventService;
    private Map<String, Set<BreakpointAddress>> breakPoints = Maps.newHashMap();
    private Set<Breakpoint> blocked = Sets.newHashSet();

    @Override
    public void block(Breakpoint breakpoint) {
        Interpreter.MAIN_LOCK.unlock();
        eventService.publish(new BreakpointReachedEvent(breakpoint, this));
        try {
            blocked.add(breakpoint);
            breakpoint.block();
        } catch (Exception ignored) {
        } finally {
            Interpreter.MAIN_LOCK.lock();
        }
    }

    @Override
    public void releaseStepOver(Breakpoint breakpoint) {
        eventService.publish(new StepOverEvent(breakpoint, this));
        release(breakpoint);
    }

    @Override
    public void releaseStepInto(Breakpoint breakpoint) {
        eventService.publish(new StepIntoEvent(breakpoint, this));
        release(breakpoint);
    }

    @Override
    public void release(Breakpoint breakpoint) {
        boolean remove = blocked.remove(breakpoint);
        eventService.publish(new BreakpointReleaseEvent(this));
        breakpoint.release();
    }

    @Subscribe
    public void onStopEvent(ReleaseBreakpointsEvent event) {
        blocked.stream().filter(breakpoint -> !breakpoint.isReleased())
                .forEach(Breakpoint::release);
        blocked.clear();
    }

    @Subscribe
    public void onExecutionStart(ExecutionStartedEvent event) {
        blocked.clear();
    }

    @Override
    public void updateBreakpoints(Code code) {
        code.instructions().forEach(instruction -> instruction.setBreakpoint(null));
        Set<BreakpointAddress> addresses = firstNonNull(breakPoints.get(code.getSourceId()), emptySet());
        for (BreakpointAddress address : addresses) {
            Instruction instr = code.instructions()
                    .filter(instruction -> nonNull(instruction.getCodeAddress()))
                    .filter(instruction -> address.getLine().equals(instruction.getCodeAddress().getLine()))
                    .findFirst().orElse(null);
            if (nonNull(instr)) {
                BreakpointImpl breakpoint = new BreakpointImpl(code.getSourceId(), instr.getCodeAddress().getLine(), instr);
                breakpoint.setCode(code);
                instr.setBreakpoint(breakpoint);
                LOGGER.info("Added breakpoints {} || {}", code.getSourceId(), instr);
            }
        }
    }

    @Override
    public Set<Integer> linesFor(String sourceId) {
        Set<BreakpointAddress> addresses = firstNonNull(breakPoints.get(sourceId), Sets.newHashSet());
        return addresses.stream().map(a -> a.getLine() - 1).collect(Collectors.toSet());
    }

    @Override
    public boolean isBreakPointExists(int value, String scriptId) {
        Set<BreakpointAddress> addresses = breakPoints.get(scriptId);
        return !isEmpty(addresses) && addresses.stream().anyMatch(address -> address.getLine().equals(value));
    }

    @Override
    public void add(String scriptId, int line) {
        Set<BreakpointAddress> addresses = breakPoints.get(scriptId);
        if (addresses == null) {
            breakPoints.put(scriptId, Sets.newHashSet(new BreakpointAddress(line)));
        } else {
            addresses.add(new BreakpointAddress(line));
        }
        eventService.publish(new BreakpointUpdatedEvent(new BreakpointImpl(scriptId, line, null), this));
        LOGGER.info("ADDED: {} | {}", scriptId, line);
    }

    @Override
    public boolean remove(String scriptId, int line) {
        Set<BreakpointAddress> addresses = breakPoints.get(scriptId);
        boolean removed = isNotEmpty(addresses) && addresses.removeIf(address -> address.getLine().equals(line));
        if (removed) {
            eventService.publish(new BreakpointUpdatedEvent(new BreakpointImpl(scriptId, line, null), this));
            LOGGER.info("REMOVED: {} | {}", scriptId, line);
            if (addresses.isEmpty()) {
                breakPoints.remove(scriptId);
                LOGGER.info("CLEARED BREAK POINTS: {}", scriptId);
            }
        }
        return removed;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}