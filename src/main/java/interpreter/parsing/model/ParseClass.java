package interpreter.parsing.model;

public enum ParseClass {

    NUMBER(0),
    OPERATOR(1),
    MATRIX_START(2),
    MATRIX_END(3),
    MATRIX_VERSE(4),
    MATRIX(5),
    IDENTIFIER(6),
    OPEN_PARENTHESIS(7),
    IF(8),
    END_IF(9),
    ELSE_KEYWORD(10);

    private int index;

    ParseClass(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
