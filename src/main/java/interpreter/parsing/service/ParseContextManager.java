package interpreter.parsing.service;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.ParseContext;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.ExpressionValue;

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
        parseContext.getExpressionTree().add(expressionValue);
    }

    public void incrementTokenPosition(int value) {
        parseContext.incrementIndex(value);
    }

    public boolean endOfTokens() {
        return parseContext.getTokensIndex() >= parseContext.getTokensLength();
    }

}
