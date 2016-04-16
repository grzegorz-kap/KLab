package com.klab.interpreter.service.functions.exception;

import com.klab.interpreter.commons.exception.InterpreterException;
import com.klab.interpreter.service.functions.InternalFunction;

public class InternalFunctionCallException extends InterpreterException {

    private static final long serialVersionUID = 1L;
    private InternalFunction internalFunction;

    public InternalFunctionCallException(InternalFunction internalFunction) {
        this.internalFunction = internalFunction;
    }

    public InternalFunctionCallException(InternalFunction internalFunction, Throwable cause) {
        super(cause);
        this.internalFunction = internalFunction;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + String.format(". Error in '%s' function call.", internalFunction.getName());
    }

}
