package interpreter.parsing.model;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.expression.Expression;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParseContext {

    private TokenList tokenList;
    private List<Expression<ParseToken>> expressionTree = new ArrayList<>();
    private Deque<ParseToken> stack = new ArrayDeque<>();
    private int index;

    public ParseContext(TokenList tokenList) {
        this.tokenList = tokenList;
        index = 0;
    }

    public void incrementIndex(int value) {
        index += value;
    }

    public int getTokensIndex() {
        return index;
    }

    public Token getCurrentToken() {
        return tokenList.get(index);
    }

    public int getTokensLength() {
        return tokenList.size();
    }

    public Token tokenAt(int offset) {
        int effectiveIndex = index + offset;
        return effectiveIndex >= 0 && effectiveIndex < getTokensLength() ? tokenList.get(effectiveIndex) : null;
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

    public List<Expression<ParseToken>> getExpressionTree() {
        return expressionTree;
    }

    public void setExpressionTree(List<Expression<ParseToken>> expressionTree) {
        this.expressionTree = expressionTree;
    }
}
