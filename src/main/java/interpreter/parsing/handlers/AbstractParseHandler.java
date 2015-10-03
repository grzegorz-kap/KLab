package interpreter.parsing.handlers;

import interpreter.parsing.service.ParseContextManager;

public abstract class AbstractParseHandler implements ParseHandler {

    protected ParseContextManager parseContextManager;

    public ParseContextManager getContextManager() {
        return parseContextManager;
    }

    @Override
    public void setContextManager(ParseContextManager parseContextManager) {
        this.parseContextManager = parseContextManager;
    }

    @Override
    public void handleStackFinish() {

    }
}
