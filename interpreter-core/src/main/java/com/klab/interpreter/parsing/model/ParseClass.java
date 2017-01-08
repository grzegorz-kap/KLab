package com.klab.interpreter.parsing.model;

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
    ELSE_KEYWORD(10),
    ELSEIF_KEYWORD(11),
    CALL(12),
    CALL_START(13),
    FOR_KEYWORD(14),
    ENDFOR_KEYWORD(15),
    CONTINUE_FOR(16),
    BREAK_FOR(17),
    MATRIX_ALL(18),
    LAST_CELL(19),
    STRING(20);

    private int index;

    ParseClass(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
