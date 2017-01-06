package com.klab.interpreter.translate.model;

public class ReverseStoreInstruction extends Instruction {
    public ReverseStoreInstruction(boolean print) {
        super(InstructionCode.REVERSE_STORE, 0);
        setPrint(print);
    }
}
