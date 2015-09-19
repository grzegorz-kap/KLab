package interpreter.translate.model;

import interpreter.translate.model.instruction.Instruction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class MacroInstruction {
    private List<Instruction> instructions = new ArrayList<>();

    public Instruction get(int index) {
        return instructions.get(index);
    }

    public void add(Instruction instruction) {
        instructions.add(instruction);
    }

    public void forEach(Consumer<? super Instruction> consumer) {
        instructions.forEach(consumer);
    }

    public Collection<? extends Instruction> getInstructions() {
        return instructions;
    }
}
