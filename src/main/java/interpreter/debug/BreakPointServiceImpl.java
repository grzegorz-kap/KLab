package interpreter.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import common.EventService;
import interpreter.execution.model.Code;
import interpreter.translate.model.Instruction;
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
class BreakpointServiceImpl implements BreakpointService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BreakpointService.class);
    private EventService eventService;
    private Map<String, Set<BreakpointAddress>> breakPoints = Maps.newHashMap();

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
        LOGGER.info("ADDED: {} | {}", scriptId, line);
    }

    @Override
    public boolean remove(String scriptId, int line) {
        Set<BreakpointAddress> addresses = breakPoints.get(scriptId);
        boolean removed = isNotEmpty(addresses) && addresses.removeIf(address -> address.getLine().equals(line));
        if (removed) {
            eventService.publish(new BreakpointUpdatedEvent(new Breakpoint(scriptId, line), this));
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