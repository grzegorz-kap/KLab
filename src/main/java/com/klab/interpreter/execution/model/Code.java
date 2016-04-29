package com.klab.interpreter.execution.model;

import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.translate.model.Instruction;
import com.klab.interpreter.translate.model.MacroInstruction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Code implements Iterable<Instruction> {
    private List<Instruction> instructions = new ArrayList<>();
    private String sourceCode;
    private String sourceId;

    @Override
    public Iterator<Instruction> iterator() {
        return instructions.iterator();
    }

    public Stream<Instruction> instructions() {
        return instructions.stream();
    }

    public void add(Instruction instruction, CodeAddress codeAddress) {
        if (codeAddress != null) {
            instruction.setCodeAddress(codeAddress);
        }
        instructions.add(instruction);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int address = 0;
        for (Instruction instruction : instructions) {
            builder.append("\n").append(address++).append(") ").append(instruction);
        }
        return builder.toString();
    }

    public void add(MacroInstruction macroInstruction) {
        macroInstruction.forEach(instructions::add);
    }

    public Instruction getAtAddress(int address) {
        return instructions.get(address);
    }

    public int size() {
        return instructions.size();
    }

    public void clear() {
        instructions.clear();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
}
