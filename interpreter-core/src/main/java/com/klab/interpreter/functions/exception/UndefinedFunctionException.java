package com.klab.interpreter.functions.exception;

import com.klab.interpreter.translate.model.CallInstruction;

public class UndefinedFunctionException extends RuntimeException {

    public static final String MESSAGE_FORMAT = "No function '%s' found which takes '%d' arguments";
    private static final long serialVersionUID = 834347388412197049L;
    private CallInstruction instruction;

    public UndefinedFunctionException(CallInstruction callInstruction) {
        this.instruction = callInstruction;
    }

    @Override
    public String toString() {
        return String.format(MESSAGE_FORMAT, instruction.getName(), instruction.getArgumentsNumber());
    }

    @Override
    public String getMessage() {
        return toString();
    }

}
