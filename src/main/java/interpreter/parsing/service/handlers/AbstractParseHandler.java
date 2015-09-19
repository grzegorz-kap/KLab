package interpreter.parsing.service.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.service.ParseContextManager;

public abstract class AbstractParseHandler implements ParseHandler {

    protected ParseContextManager parseContextManager;
    protected TokenClass supportedTokenClass;

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

    @Override
    public TokenClass getSupportedTokenClass() {
        return supportedTokenClass;
    }
}
