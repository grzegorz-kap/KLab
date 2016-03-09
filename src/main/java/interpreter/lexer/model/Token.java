package interpreter.lexer.model;

public class Token {
    private String lexeme;
    private CodeAddress address;
    private TokenClass tokenClass;
    private boolean isKeyword = false;

    @Override
    public String toString() {
        return lexeme + "\t" + tokenClass + "\t" + address.getLine() + "\t" + address.getColumn();
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public CodeAddress getAddress() {
        return address;
    }

    public void setAddress(CodeAddress address) {
        this.address = address;
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
