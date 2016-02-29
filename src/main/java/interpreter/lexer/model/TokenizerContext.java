package interpreter.lexer.model;

public class TokenizerContext {

    protected String inputText;
    protected int index = 0;
    protected int length = 0;
    private TokenList tokenList = new TokenList();
    private int line = 1;
    private int column = 1;

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

    public CodeAddress currentAddress() {
        return new CodeAddress(line, column);
    }

    public boolean isInputEnd() {
        return index >= length;
    }

    public TokenList getTokenList() {
        return tokenList;
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

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getInputText() {
        return inputText;
    }
}
