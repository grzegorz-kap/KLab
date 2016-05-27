package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.service.ParseContextManager;

public abstract class AbstractParseHandler implements ParseHandler {
    protected ParseContextManager pCtxMgr;

    public ParseContextManager getContextManager() {
        return pCtxMgr;
    }

    @Override
    public void setContextManager(ParseContextManager parseContextManager) {
        this.pCtxMgr = parseContextManager;
    }

    @Override
    public void handleStackFinish() {
        throw new UnsupportedOperationException();
    }

    protected TokenClass tokenClassAt(int offset) {
        Token token = pCtxMgr.tokenAt(offset);
        return token != null ? token.getTokenClass() : null;
    }
}
