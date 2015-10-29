package interpreter.translate.model;

public enum InstructionCode {
    PUSH(0),
    ADD(1),
    SUB(2),
    MULT(3),
    DIV(4),
    PRINT(5),
    MATRIX(6),
    MATRIX_VERSE(7),
    LOAD(8),
    STORE(9),
    EQ(10),
    NEQ(11),
    GT(12),
    GE(13),
    LE(14),
    LT(15),
    JMPF(16),
    JMP(17),
    CALL(18),
    ANS(19);

    private int index;

    InstructionCode(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
