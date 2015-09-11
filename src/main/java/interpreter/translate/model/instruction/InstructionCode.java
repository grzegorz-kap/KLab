package interpreter.translate.model.instruction;

public enum InstructionCode {
    PUSH(0),
    ADD(1),
    SUB(2),
    MULT(3),
    DIV(4);

    private int index;

    InstructionCode(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
