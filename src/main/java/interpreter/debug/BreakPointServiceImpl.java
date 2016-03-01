package interpreter.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import common.EventService;
import interpreter.execution.model.Code;
import interpreter.translate.model.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.MoreObjects.firstNonNull;
import static interpreter.debug.BreakpointEvent.Operation.ADD;
import static interpreter.debug.BreakpointEvent.Operation.REMOVE;
import static java.util.Collections.emptySet;
import static java.util.Objects.nonNull;

@Service
class BreakpointServiceImpl implements BreakpointService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BreakpointService.class);
    private EventService eventService;
    Map<String, Set<BreakpointAddress>> breakPoints = Maps.newHashMap();

    @Override
    public void updateBreakpoints(Code code) {
        code.instructions().forEach(instruction -> instruction.setBreakpoint(false));
        Set<BreakpointAddress> addresses = firstNonNull(breakPoints.get(code.getSourceId()), emptySet());
        for (BreakpointAddress address : addresses) {
            Instruction instr = code.instructions()
                    .filter(instruction -> nonNull(instruction.getCodeAddress()))
                    .filter(instruction -> address.getLine().equals(instruction.getCodeAddress().getLine()))
                    .findFirst().orElse(null);
            if (nonNull(instr)) {
                instr.setBreakpoint(true);
                LOGGER.info("Added breakpoints {} || {}", code.getSourceId(), instr);
            }
        }
    }

    @Subscribe
    private void onBreakPointChanged(BreakpointEvent event) {
        Breakpoint breakpoint = event.getData();
        Set<BreakpointAddress> addresses = breakPoints.get(breakpoint.getSourceId());
        if (ADD.equals(event.getOperation())) {
            onAdd(breakpoint, addresses);
        } else if (addresses != null && REMOVE.equals(event.getOperation())) {
            onRemove(breakpoint, addresses);
        } else {
            LOGGER.error("Unknown event data: {}", breakpoint);
            throw new RuntimeException(); // // TODO: 29.02.2016
        }
        eventService.publish(new BreakPointsUpdatedEvent(breakpoint, this));
    }

    private void onAdd(Breakpoint breakpoint, Set<BreakpointAddress> addresses) {
        if (addresses == null) {
            breakPoints.put(breakpoint.getSourceId(), Sets.newHashSet(breakpoint.getAddress()));
        } else {
            addresses.add(breakpoint.getAddress());
        }
        LOGGER.info("ADDED: {}", breakpoint);
    }

    private void onRemove(Breakpoint breakpoint, Set<BreakpointAddress> addresses) {
        addresses.remove(breakpoint.getAddress());
        LOGGER.info("REMOVED: {}", breakpoint);
        if (addresses.isEmpty()) {
            breakPoints.remove(breakpoint.getSourceId());
            LOGGER.info("CLEARED BREAK POINTS: {}", breakpoint.getSourceId());
        }
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}