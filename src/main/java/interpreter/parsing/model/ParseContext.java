package interpreter.parsing.model;

import interpreter.lexer.model.TokenList;

import java.util.ArrayDeque;
import java.util.Deque;

public class ParseContext {

    private TokenList tokenList;
    private ExpressionTree<ParseToken> expressionTree = new ExpressionTree<>();
    private Deque<ParseToken> stack = new ArrayDeque<>();

    public ParseContext(TokenList tokenList) {
        this.tokenList = tokenList;
    }

    public ExpressionTree<ParseToken> getExpressionTree() {
        return expressionTree;
    }

    public ParseToken stackPeek() {
        return stack.peek();
    }

    public ParseToken stackPop() {
        return stack.pop();
    }

    public void stackPush(ParseToken parseToken) {
        stack.push(parseToken);
    }

    public int stackSize() {
        return stack.size();
    }
}
