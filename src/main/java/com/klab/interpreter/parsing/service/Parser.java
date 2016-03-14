package com.klab.interpreter.parsing.service;

import com.klab.interpreter.lexer.model.TokenList;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;

import java.util.List;

public interface Parser {
    List<Expression<ParseToken>> process();

    void setTokenList(TokenList tokenList);

    boolean hasNext();
}
