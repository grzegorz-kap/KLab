package interpreter.parsing.service.handlers;

import interpreter.parsing.service.ParseContextManager;

public abstract class AbstractParseHandler implements ParseHandler {

    private ParseContextManager parseContextManager;

    public ParseContextManager getContextManager() {
        return parseContextManager;
    }

    @Override
    public void setContextManager(ParseContextManager parseContextManager) {
        this.parseContextManager = parseContextManager;
    }
}
