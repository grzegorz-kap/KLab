package interpreter.parsing.service;

import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.ExpressionTree;
import interpreter.parsing.model.ParseToken;

public class AbstractParser implements Parser {
    @Override
    public ExpressionTree<ParseToken> process(TokenList tokenList) {
        return null;
    }
}
