package com.klab.interpreter.parsing.service;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.lexer.model.TokenList;
import com.klab.interpreter.parsing.handlers.ParseHandler;
import com.klab.interpreter.parsing.model.ParseContext;

import java.util.Objects;
import java.util.Set;

public abstract class AbstractParser implements Parser {

    protected ParseContext parseContext;
    protected ParseContextManager parseContextManager;
    private ParseHandler[] parseHandlers = new ParseHandler[TokenClass.values().length];

    public AbstractParser(Set<ParseHandler> parseHandlers, ParseContextManager parseContextManager) {
        this.parseContextManager = parseContextManager;
        parseHandlers.stream()
                .filter(parseHandler -> Objects.nonNull(parseHandler.getSupportedTokenClass()))
                .forEach(this::addParseHandler);
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
