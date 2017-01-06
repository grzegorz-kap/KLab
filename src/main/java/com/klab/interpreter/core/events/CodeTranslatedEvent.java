package com.klab.interpreter.core.events;

import com.klab.interpreter.execution.model.Code;

public class CodeTranslatedEvent extends InterpreterEvent<Code> {
    public CodeTranslatedEvent(Code code, Object source) {
        super(code, source);
    }
}
