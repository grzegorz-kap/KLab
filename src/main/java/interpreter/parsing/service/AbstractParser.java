package interpreter.parsing.service;

import interpreter.lexer.model.TokenClass;
import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.ExpressionTree;
import interpreter.parsing.model.ParseContext;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.service.handlers.ParseHandler;

import java.util.Map;

public class AbstractParser implements Parser {

    private ParseContext parseContext;

    private Map<TokenClass, ParseHandler> parseHandlerMap;

    @Override
    public ExpressionTree<ParseToken> process(TokenList tokenList) {
        parseContext = new ParseContext(tokenList);
        return parseContext.getExpressionTree();
    }

    @Override
    public ParseHandler getParseHandler(TokenClass tokenClass) {
        return parseHandlerMap.get(tokenClass);
    }

    @Override
    public void addParseHandler(TokenClass tokenClass, ParseHandler parseHandler) {
        parseHandler.setParser(this);
        parseHandlerMap.put(tokenClass, parseHandler);
    }
}
