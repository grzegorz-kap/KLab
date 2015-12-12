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
    ANS(19),
    FLNEXT(20),
    FLINIT(21),
    CLEAR(22),
    RANGE(23),
    RANGE3(24),
    AMULT(25),
    ADIV(26),
    POW(27),
    APOW(28),
    TRANSPOSE(29),
    AAND(30),
    AOR(31),
    JMPFNP(32),
    JMPTNP(33),
    LOGICAL(34),
    NOT(35),
    NEG(36),
    SCRIPT_EXIT(37);

    private int index;

    InstructionCode(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
