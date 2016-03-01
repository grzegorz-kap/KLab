package interpreter.debug;

import interpreter.execution.model.Code;

public interface BreakpointService {
    void updateBreakpoints(Code code);
}