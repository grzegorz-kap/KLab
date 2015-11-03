package interpreter.parsing.service;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.exception.WrongNumberOfArgumentsException;
import interpreter.parsing.handlers.ParseHandler;
import interpreter.parsing.model.BalanceContext;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseContext;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionNode;
import interpreter.parsing.model.expression.ExpressionValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ParseContextManager {

    private ParseContext parseContext;

    public Token tokenAt(int offset) {
        return parseContext.tokenAt(offset);
    }

    public void addExpressionValue(ParseToken parseToken) {
        ExpressionValue<ParseToken> expressionValue = new ExpressionValue<>();
        expressionValue.setValue(parseToken);
        parseContext.addExpression(expressionValue);
    }

    public void addExpressionNode(ParseToken parseToken) {
        parseContext.addExpression(new ExpressionNode<>(parseToken));
    }

    public void addExpression(Expression<ParseToken> expression) {
        parseContext.addExpression(expression);
    }

    public void incrementTokenPosition(int value) {
        parseContext.incrementIndex(value);
    }

    public boolean isEndOfTokens() {
        return parseContext.getTokensIndex() >= parseContext.getTokensLength();
    }

    public boolean isStackEmpty() {
        return parseContext.stackSize() == 0;
    }

    public ParseToken stackPeek() {
        return parseContext.stackPeek();
    }

    public ParseToken stackPop() {
        return parseContext.stackPop();
    }

    public ParseClass stackPeekClass() {
        return parseContext.stackPeek().getParseClass();
    }

    public TokenClass stackPeekTokenClass() {
        return parseContext.stackPeek().getTokenClass();
    }

    public void stackPush(ParseToken parseToken) {
        parseContext.stackPush(parseToken);
    }

    public int stackSize() {
        return parseContext.stackSize();
    }

    public void checkIfCorrectNumberOfArguments(int expectedArgumentsNumber) {
        if (parseContext.expressionSize() < expectedArgumentsNumber) {
            throw new WrongNumberOfArgumentsException("Wrong number of arguments.", parseContext);
        }
    }

    public Expression<ParseToken> expressionPop() {
        return expressionPopArguments(1).get(0);
    }

    public Expression<ParseToken> expressionPeek() {
        return parseContext.getExpression(expressionSize() - 1);
    }

    public List<Expression<ParseToken>> expressionPopArguments(int argumentsNumber) {
        checkIfCorrectNumberOfArguments(argumentsNumber);
        List<Expression<ParseToken>> subList = parseContext.getLastFromExpression(argumentsNumber);
        parseContext.removeLastFromExpression(argumentsNumber);
        return subList;
    }

    public ParseHandler getParseHandler(TokenClass tokenClass) {
        return parseContext.getParseHandler(tokenClass);
    }

    public Integer findLast(Predicate<? super Expression<ParseToken>> predicate) {
        ListIterator<Expression<ParseToken>> iterator = parseContext.getListIterator(parseContext.expressionSize());
        int index = parseContext.expressionSize();
        while (iterator.hasPrevious()) {
            --index;
            if (predicate.test(iterator.previous())) {
                return index;
            }
        }
        return null;
    }

    public int expressionSize() {
        return parseContext.expressionSize();
    }

    public BalanceContext getBalanceContext() {
        return parseContext.getBalanceContext();
    }

    public void setInstructionStop(boolean instructionStop) {
        parseContext.setInstructionStop(instructionStop);
    }

    public void setInstructionPrint(boolean printFlag) {
        parseContext.setInstructionPrint(printFlag);
    }

    public ParseContext getParseContext() {
        return parseContext;
    }

    public void setParseContext(ParseContext parseContext) {
        this.parseContext = parseContext;
    }
}
