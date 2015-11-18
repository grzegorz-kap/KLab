package interpreter.lexer.model;

public enum TokenClass {
    NUMBER(0),
    WORD(1),
    OPERATOR(2),
    SPACE(3),
    COMMA(4),
    COLON(5),
    OPEN_PARENTHESIS(6),
    CLOSE_PARENTHESIS(7),
    SEMICOLON(8),
    OPEN_BRACKET(9),
    CLOSE_BRACKET(10),
    NEW_LINE(11),
    IF_KEYWORD(12),
    ENDIF_KEYWORD(13),
    ELSE_KEYWORD(14),
    ELSEIF_KEYWORD(15),
    FOR_KEYWORD(16),
    ENDFOR_KEYWORD(17),
    BREAK_KEYWORD(18),
    CONTINUE_KEYWORD(19);

    private int index;

    TokenClass(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
