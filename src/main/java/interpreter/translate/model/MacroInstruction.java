package interpreter.translate.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import interpreter.translate.model.instruction.Instruction;

public class MacroInstruction {
	private List<Instruction> instructions = new ArrayList<>();

	public Instruction get(int index) {
		return instructions.get(index);
	}

	public MacroInstruction add(Instruction instruction) {
		instructions.add(instruction);
		return this;
	}

	public MacroInstruction add(MacroInstruction macroInstruction) {
		instructions.addAll(macroInstruction.getInstructions());
		return this;
	}

	public void forEach(Consumer<? super Instruction> consumer) {
		instructions.forEach(consumer);
	}

	public Collection<? extends Instruction> getInstructions() {
		return instructions;
	}
}
