package interpreter.execution.model;

import interpreter.translate.model.Instruction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Code implements Iterable<Instruction> {
    private List<Instruction> instructions = new ArrayList<>();
    private String source;

    @Override
    public Iterator<Instruction> iterator() {
        return instructions.iterator();
    }

    public Stream<Instruction> instructions() {
        return instructions.stream();
    }

    public void add(Instruction instruction) {
        instructions.add(instruction);
    }

    public void add(Collection<? extends Instruction> instructions) {
        this.instructions.addAll(instructions);
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
