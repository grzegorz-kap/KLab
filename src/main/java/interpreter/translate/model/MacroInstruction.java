package interpreter.translate.model;

import interpreter.translate.model.instruction.Instruction;

import java.util.ArrayList;
import java.util.List;

public class MacroInstruction {
    private List<Instruction> instructions = new ArrayList<>();

    public Instruction get(int index) {
        return instructions.get(index);
    }

    public void add(Instruction instruction) {
        instructions.add(instruction);
    }
}
