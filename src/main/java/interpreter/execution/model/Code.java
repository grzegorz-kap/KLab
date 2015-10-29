package interpreter.execution.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import interpreter.translate.model.Instruction;

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

    public void clear() {
        instructions.clear();
    }
    
    @Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	int address = 0;
    	for(Instruction instruction : instructions) {
    		builder.append("\n").append(address++).append(") ").append(instruction);
    	}
    	return builder.toString();
    }
}
