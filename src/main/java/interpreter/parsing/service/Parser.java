package interpreter.parsing.service;

import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.ExpressionTree;
import interpreter.parsing.model.ParseToken;

public interface Parser {
    ExpressionTree<ParseToken> process(TokenList tokenList);
}
