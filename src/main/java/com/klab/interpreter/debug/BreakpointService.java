package com.klab.interpreter.debug;

import com.klab.interpreter.execution.model.Code;

import java.util.Set;

public interface BreakpointService {
    void updateBreakpoints(Code code);

    Set<Integer> linesFor(String sourceId);

    boolean isBreakPointExists(int value, String scriptId);

    void add(String scriptId, int line);

    boolean remove(String scriptId, int line);

    void block(Breakpoint breakpoint);

    void release(Breakpoint breakpoint);

    void releaseStepOver(Breakpoint breakpoint);

    void releaseStepInto(Breakpoint breakpoint);
}