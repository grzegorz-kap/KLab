package interpreter.execution.model;

import interpreter.translate.model.Instruction;

public class InstructionPointer {

    private Code code;
    private int current = 0;

    public InstructionPointer(Code code) {
        this.code = code;
    }

    public boolean isCodeEnd() {
        return code.size() <= current;
    }

    public Instruction current() {
        return code.getAtAddress(current);
    }

    public void increment() {
        current++;
    }

    public void jumpTo(int address) {
        current = address;
    }

}
