package com.klab.interpreter.execution.exception;

import com.klab.interpreter.types.ObjectData;

public class WrongTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WrongTypeException(ObjectData objectData) {
    }
}
