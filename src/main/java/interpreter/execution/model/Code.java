package interpreter.execution.model;

import interpreter.lexer.model.CodeAddress;
import interpreter.translate.model.Instruction;
import interpreter.translate.model.MacroInstruction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Code implements Iterable<Instruction> {
    private List<Instruction> instructions = new ArrayList<>();
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int address = 0;
        for (Instruction instruction : instructions) {
            builder.append("\n").append(address++).append(") ").append(instruction);
        }
        return builder.toString();
    }
}
