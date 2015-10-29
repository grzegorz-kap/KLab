package interpreter.parsing.service;

import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;

import java.util.List;

public interface Parser {
    List<Expression<ParseToken>> process();

    void setTokenList(TokenList tokenList);

    boolean hasNext();
}
