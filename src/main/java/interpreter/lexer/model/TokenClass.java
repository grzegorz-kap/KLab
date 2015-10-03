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
    NEW_LINE(11);

    private int index;

    TokenClass(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
