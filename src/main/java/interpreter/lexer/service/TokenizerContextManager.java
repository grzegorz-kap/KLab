package interpreter.lexer.service;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenizerContext;

public class TokenizerContextManager {
    private TokenizerContext tokenizerContext;

    public TokenizerContextManager(TokenizerContext tokenizerContext) {
        this.tokenizerContext = tokenizerContext;
    }

    public void addToken(Token token) {
        token.setLine(tokenizerContext.getLine());
        token.setColumn(tokenizerContext.getColumn());
        tokenizerContext.addToken(token);
        incrementTextPosition(token.getLexeme().length());
        process(token);
    }

    private void process(final Token token) {
        switch (token.getTokenClass()) {
            case NEW_LINE:
                newLineProcess(token);
                break;
            default:
                standardProcess(token);
        }
    }

    private void standardProcess(final Token token) {
        incrementColumn(token.getLexeme().length());
    }

    private void newLineProcess(final Token token) {
        int newLines = (int) token.getLexeme().chars().filter(character -> character == '\n').count();
        int lastLine = token.getLexeme().lastIndexOf('\n');
        incrementLine(newLines);
        tokenizerContext.setColumn((long) (token.getLexeme().length() - lastLine));
    }

    private void incrementLine(int value) {
        tokenizerContext.setLine(tokenizerContext.getLine() + value);
    }

    private void incrementColumn(int value) {
        tokenizerContext.setColumn(tokenizerContext.getColumn() + value);
    }

    private void incrementTextPosition(int value) {
        tokenizerContext.setIndex(tokenizerContext.getIndex() + value);
    }

}
