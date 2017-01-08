package com.klab.interpreter.lexer.service;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.lexer.model.TokenizerContext;

public class TokenizerContextManager {
    private TokenizerContext tokenizerContext;

    public TokenizerContextManager(TokenizerContext tokenizerContext) {
        this.tokenizerContext = tokenizerContext;
    }

    public void addToken(Token token) {
        token.setAddress(tokenizerContext.currentAddress());
        tokenizerContext.addToken(token);
        tokenizerContext.setIndex(tokenizerContext.getIndex() + token.getLexeme().length());
        if (token.getTokenClass() == TokenClass.NEW_LINE) {
            int newLines = (int) token.getLexeme().chars().filter(character -> character == '\n').count();
            int lastLine = token.getLexeme().lastIndexOf('\n');
            tokenizerContext.setLine(tokenizerContext.getLine() + newLines);
            tokenizerContext.setColumn(token.getLexeme().length() - lastLine);
        } else {
            tokenizerContext.setColumn(tokenizerContext.getColumn() + token.getLexeme().length());
        }
    }

    TokenClass tokenClassAt(int offset) {
        int index = tokenizerContext.getTokenList().size() + offset - 1;
        return index < 0 || index >= tokenizerContext.getTokenList().size()
                ? null : tokenizerContext.getTokenList().get(index).getTokenClass();
    }
}
