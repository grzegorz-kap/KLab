package interpreter.parsing.service;

import interpreter.lexer.model.TokenClass;
import interpreter.lexer.model.TokenList;
import interpreter.parsing.handlers.ParseHandler;
import interpreter.parsing.model.ParseContext;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;

import java.util.Set;

public abstract class AbstractParser implements Parser {

    protected ParseContext parseContext;
    protected ParseContextManager parseContextManager;
    private ParseHandler[] parseHandlers = new ParseHandler[TokenClass.values().length];

    public AbstractParser(Set<ParseHandler> parseHandlers, ParseContextManager parseContextManager) {
        this.parseContextManager = parseContextManager;
        parseHandlers.forEach(this::addParseHandler);
    }

    protected abstract void process();

    @Override
    public Expression<ParseToken> process(TokenList tokenList) {
        parseContext = new ParseContext(tokenList);
        parseContext.setParseHandlers(parseHandlers);
        parseContextManager.setParseContext(parseContext);
        process();
        return parseContext.getLastFromExpression(1).get(0);
    }

    public ParseHandler getParseHandler(TokenClass tokenClass) {
        return parseHandlers[tokenClass.getIndex()];
    }

    private void addParseHandler(ParseHandler parseHandler) {
        parseHandler.setContextManager(parseContextManager);
        parseHandlers[parseHandler.getSupportedTokenClass().getIndex()] = parseHandler;
    }
}
