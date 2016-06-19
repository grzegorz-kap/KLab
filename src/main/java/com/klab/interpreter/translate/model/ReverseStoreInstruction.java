package com.klab.interpreter.translate.model;

public class ReverseStoreInstruction extends Instruction {
    private boolean print;

    public ReverseStoreInstruction(boolean print) {
        super(InstructionCode.REVERSE_STORE, 0);
        this.print = print;
    }

    public boolean isPrint() {
        return print;
    }
}
