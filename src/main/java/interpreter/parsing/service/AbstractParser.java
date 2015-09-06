package interpreter.parsing.service;

import interpreter.lexer.model.TokenClass;
import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.ParseContext;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.service.handlers.ParseHandler;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractParser implements Parser {

    protected ParseContext parseContext;

    protected ParseContextManager parseContextManager = new ParseContextManager();

    private Map<TokenClass, ParseHandler> parseHandlerMap = new HashMap<>();

    protected abstract void process();

    @Override
    public Expression<ParseToken> process(TokenList tokenList) {
        parseContext = new ParseContext(tokenList);
        parseContextManager.setParseContext(parseContext);
        process();
        return parseContext.getLastFromExpression(1).get(0);
    }

    @Override
    public ParseHandler getParseHandler(TokenClass tokenClass) {
        return parseHandlerMap.get(tokenClass);
    }

    @Override
    public void addParseHandler(TokenClass tokenClass, ParseHandler parseHandler) {
        parseHandler.setContextManager(parseContextManager);
        parseHandlerMap.put(tokenClass, parseHandler);
    }
}
