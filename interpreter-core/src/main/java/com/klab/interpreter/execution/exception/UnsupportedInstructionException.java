package com.klab.interpreter.execution.exception;

import com.klab.interpreter.translate.model.Instruction;

public class UnsupportedInstructionException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Instruction instruction;

    public UnsupportedInstructionException(String message, Instruction instruction) {
        super(message);
        this.instruction = instruction;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " ," + instruction.getInstructionCode();
    }
}
