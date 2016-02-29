package interpreter.debug;

import interpreter.execution.model.Code;

public interface BreakPointService {
    void updateBreakpoints(Code code);
}