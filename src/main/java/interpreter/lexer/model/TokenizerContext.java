package interpreter.lexer.model;

public class TokenizerContext {

    protected String inputText;
    protected int index = 0;
    protected int length = 0;
    private TokenList tokenList = new TokenList();
    private Long line = (long) 1;
    private Long column = (long) 1;

    public TokenizerContext(String inputText) {
        this.inputText = inputText;
        this.length = inputText.length();
    }

    public char charAt(int offset) {
        return index + offset < length ? inputText.charAt(index + offset) : '\0';
    }

    public boolean isCharAt(int offset, char character) {
        return index + offset < length && inputText.charAt(index + offset) == character;
    }

    public boolean isInputEnd() {
        return index >= length;
    }

    public TokenList getTokenList() {
        return tokenList;
    }

    public void setTokenList(TokenList tokenList) {
        this.tokenList = tokenList;
    }

    public void addToken(Token token) {
        tokenList.add(token);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public String getInputText() {
        return inputText;
    }
}
