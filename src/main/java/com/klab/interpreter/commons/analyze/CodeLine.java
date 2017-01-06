package com.klab.interpreter.commons.analyze;

import com.klab.interpreter.translate.model.Instruction;

import java.util.Collection;

public class CodeLine {
    private String line;
    private int number;
    private Collection<Instruction> instructions;

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Collection<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(Collection<Instruction> instructions) {
        this.instructions = instructions;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
