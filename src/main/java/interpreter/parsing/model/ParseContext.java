package interpreter.parsing.model;

import interpreter.lexer.model.TokenList;

public class ParseContext {

    private TokenList tokenList;
    private ExpressionTree<ParseToken> expressionTree = new ExpressionTree<>();

    public ParseContext(TokenList tokenList) {
        this.tokenList = tokenList;
    }
}
