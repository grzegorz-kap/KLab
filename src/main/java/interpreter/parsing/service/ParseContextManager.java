package interpreter.parsing.service;

import interpreter.lexer.model.Token;
import interpreter.parsing.exception.WrongNumberOfArgumentsException;
import interpreter.parsing.model.ParseContext;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.expression.ExpressionValue;

import java.util.List;

public class ParseContextManager {

    private ParseContext parseContext;

    public void setParseContext(ParseContext parseContext) {
        this.parseContext = parseContext;
    }

    public Token tokenAt(int offset) {
        return parseContext.tokenAt(offset);
    }

    public void addExpressionValue(ParseToken parseToken) {
        ExpressionValue<ParseToken> expressionValue = new ExpressionValue<>();
        expressionValue.setValue(parseToken);
        parseContext.addExpression(expressionValue);
    }

    public void addExpression(Expression<ParseToken> expression) {
        parseContext.addExpression(expression);
    }

    public void incrementTokenPosition(int value) {
        parseContext.incrementIndex(value);
    }

    public boolean endOfTokens() {
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

    public List<Expression<ParseToken>> popExpressionArguments(int argumentsNumber) {
        checkIfCorrectNumberOfArguments(argumentsNumber);
        List<Expression<ParseToken>> subList = parseContext.getLastFromExpression(argumentsNumber);
        parseContext.removeLastFromExpression(argumentsNumber);
        return subList;
    }

}
