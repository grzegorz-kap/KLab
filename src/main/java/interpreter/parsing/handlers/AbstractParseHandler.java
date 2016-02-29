package interpreter.parsing.handlers;

import interpreter.parsing.service.ParseContextManager;

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
}
