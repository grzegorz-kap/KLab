package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.model.KeywordBalance;
import com.klab.interpreter.parsing.service.ParseContextManager;

public abstract class AbstractParseHandler implements ParseHandler {
    protected ParseContextManager parseContextManager;

    TokenClass tokenClassAt(int offset) {
        Token token = parseContextManager.tokenAt(offset);
        return token != null ? token.getTokenClass() : null;
    }

    @Override
    public void setContextManager(ParseContextManager parseContextManager) {
        this.parseContextManager = parseContextManager;
    }

    boolean isKeywordBalance(KeywordBalance keywordBalance) {
        return parseContextManager.getBalanceContext().isKeywordBalance(keywordBalance);
    }
}
