package interpreter.parsing.service;

import interpreter.lexer.model.TokenClass;
import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.service.handlers.ParseHandler;

public interface Parser {
    Expression<ParseToken> process(TokenList tokenList);

    ParseHandler getParseHandler(TokenClass tokenClass);

    void addParseHandler(TokenClass tokenClass, ParseHandler parseHandler);
}
