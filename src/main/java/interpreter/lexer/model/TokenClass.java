package interpreter.lexer.model;

public enum TokenClass {
    NUMBER(0, false, false),
    WORD(1, false, false),
    OPERATOR(2, false, false),
    SPACE(3, false, false),
    COMMA(4, true, false),
    COLON(5, true, false),
    OPEN_PARENTHESIS(6, true, false),
    CLOSE_PARENTHESIS(7, false, false),
    SEMICOLON(8, true, false),
    OPEN_BRACKET(9, true, false),
    CLOSE_BRACKET(10, false, false),
    NEW_LINE(11, true, false),
    IF_KEYWORD(12, true, true),
    ENDIF_KEYWORD(13, true, true),
    ELSE_KEYWORD(14, true, true),
    ELSEIF_KEYWORD(15, true, true),
    FOR_KEYWORD(16, true, true),
    ENDFOR_KEYWORD(17, true, true),
    BREAK_KEYWORD(18, true,true),
    CONTINUE_KEYWORD(19, true, true),
    FUNCTION_KEYWROD(20, true, true);

    private int index;
    private boolean keyword;
    private boolean unaryOpPrecursor = false;

    TokenClass(int index, boolean unaryOpPrecurosr) {
    	this.unaryOpPrecursor = unaryOpPrecurosr;
        this.index = index;
        this.keyword = false;
    }
    
    TokenClass(int index, boolean unaryOpPrecurosr, boolean keyword) {
    	this.index = index;
    	this.keyword = keyword;
    }
    
    public boolean isUnaryOpPrecursor() {
		return unaryOpPrecursor;
	}
    
    public boolean isKeyword() {
		return this.keyword;
	}

    public int getIndex() {
        return index;
    }
}
