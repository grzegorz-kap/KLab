package interpreter.debug;

import interpreter.execution.model.Code;

import java.util.Set;

public interface BreakpointService {
    void updateBreakpoints(Code code);

    Set<Integer> linesFor(String sourceId);

    boolean isBreakPointExists(int value, String scriptId);

    void add(String scriptId, int line);

    boolean remove(String scriptId, int line);
}