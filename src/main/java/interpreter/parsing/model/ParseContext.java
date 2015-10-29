package interpreter.parsing.model;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.lexer.model.TokenList;
import interpreter.parsing.handlers.ParseHandler;
import interpreter.parsing.model.expression.Expression;

import java.util.*;
import java.util.function.Consumer;

public class ParseContext {

    private TokenList tokenList;
    private List<Expression<ParseToken>> expressionTree = new ArrayList<>();
    private Deque<ParseToken> stack = new ArrayDeque<>();
    private int index;
    private ParseHandler[] parseHandlers;
    private BalanceContext balanceContext = new BalanceContext();
    private boolean instructionStop;
    private boolean instructionPrint;

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

    public void addExpression(Expression<ParseToken> expression) {
        expressionTree.add(expression);
    }

    public Expression<ParseToken> getExpression(int index) {
        return expressionTree.get(index);
    }

    public List<Expression<ParseToken>> getLastFromExpression(int number) {
        List<Expression<ParseToken>> expressions = new ArrayList<>(number);
        for (int index = expressionTree.size() - number; index < expressionTree.size(); index++) {
            expressions.add(expressionTree.get(index));
        }
        return expressions;
    }

    public void removeLastFromExpression(int number) {
        int indexToRemove = expressionTree.size() - 1;
        while (number-- > 0 && indexToRemove >= 0) {
            expressionTree.remove(indexToRemove--);
        }
    }

    public int expressionSize() {
        return expressionTree.size();
    }

    public void setParseHandlers(ParseHandler[] parseHandlers) {
        this.parseHandlers = parseHandlers;
    }

    public ParseHandler getParseHandler(TokenClass tokenClass) {
        return parseHandlers[tokenClass.getIndex()];
    }

    public ListIterator<Expression<ParseToken>> getListIterator(int index) {
        return expressionTree.listIterator(index);
    }

    public BalanceContext getBalanceContext() {
        return balanceContext;
    }

    public boolean isInstructionStop() {
        return instructionStop;
    }

    public void setInstructionStop(boolean instructionStop) {
        this.instructionStop = instructionStop;
    }

    public boolean isInstructionPrint() {
        return instructionPrint;
    }

    public void setInstructionPrint(boolean instructionPrint) {
        this.instructionPrint = instructionPrint;
    }

    public void forEachExpression(Consumer<? super Expression<ParseToken>> consumer) {
        expressionTree.forEach(consumer);
    }

    public List<Expression<ParseToken>> getExpression() {
        return expressionTree;
    }
}
