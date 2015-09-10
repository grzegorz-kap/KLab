package interpreter.execution.model;

import interpreter.translate.model.instruction.Instruction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Code {

    private List<Instruction> instructions = new ArrayList<>();

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
}
