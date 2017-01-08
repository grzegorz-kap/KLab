package com.klab.interpreter.translate.model;

public class JumperInstruction extends Instruction {
    private Integer jumpIndex;

    public JumperInstruction(InstructionCode instructionCode, Integer argumentsNumber) {
        super(instructionCode, argumentsNumber);
    }

    public JumperInstruction() {
    }

    public Integer getJumpIndex() {
        return jumpIndex;
    }

    public void setJumpIndex(Integer jumpIndex) {
        this.jumpIndex = jumpIndex;
    }

    @Override
    public String toString() {
        return super.toString() + jumpIndex;
    }
}
