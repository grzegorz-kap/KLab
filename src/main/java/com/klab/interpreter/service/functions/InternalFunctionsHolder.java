package com.klab.interpreter.service.functions;

public interface InternalFunctionsHolder {
    InternalFunction get(int address);

    Integer getAddress(String functionName, int argumentsNumber);
}
