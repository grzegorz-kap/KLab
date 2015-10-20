package interpreter.lexer.model;

public class Token {

    private String lexeme;
    private Long line;
    private Long column;
    private TokenClass tokenClass;
    private boolean isKeyword = false;

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public Long getLine() {
        return line;
    }

    public void setLine(Long line) {
        this.line = line;
    }

    public Long getColumn() {
        return column;
    }

    public void setColumn(Long column) {
        this.column = column;
    }

    public TokenClass getTokenClass() {
        return tokenClass;
    }

    public void setTokenClass(TokenClass tokenClass) {
        this.tokenClass = tokenClass;
    }

    public boolean isKeyword() {
        return isKeyword;
    }

    public void setIsKeyword(boolean isKeyword) {
        this.isKeyword = isKeyword;
    }
}
