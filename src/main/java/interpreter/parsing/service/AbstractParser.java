package interpreter.parsing.service;

import interpreter.lexer.model.TokenClass;
import interpreter.lexer.model.TokenList;
import interpreter.parsing.handlers.ParseHandler;
import interpreter.parsing.model.ParseContext;

import java.util.Set;

public abstract class AbstractParser implements Parser {

    protected ParseContext parseContext;
    protected ParseContextManager parseContextManager;
    private ParseHandler[] parseHandlers = new ParseHandler[TokenClass.values().length];

    public AbstractParser(Set<ParseHandler> parseHandlers, ParseContextManager parseContextManager) {
        this.parseContextManager = parseContextManager;
        parseHandlers.forEach(this::addParseHandler);
    }

    @Override
    public void setTokenList(TokenList tokenList) {
        parseContext = new ParseContext(tokenList);
        parseContext.setParseHandlers(parseHandlers);
        parseContextManager.setParseContext(parseContext);
    }

    @Override
    public boolean hasNext() {
        return !parseContextManager.isEndOfTokens();
    }

    public ParseHandler getParseHandler(TokenClass tokenClass) {
        return parseHandlers[tokenClass.getIndex()];
    }

    private void addParseHandler(ParseHandler parseHandler) {
        parseHandler.setContextManager(parseContextManager);
        parseHandlers[parseHandler.getSupportedTokenClass().getIndex()] = parseHandler;
    }
}
