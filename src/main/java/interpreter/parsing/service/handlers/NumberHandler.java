package interpreter.parsing.service.handlers;

import interpreter.lexer.model.Token;
import interpreter.parsing.model.NumberType;
import interpreter.parsing.model.tokens.NumberToken;

public class NumberHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        Token token = getContextManager().tokenAt(0);
        NumberToken numberToken = createNumberToken(token);
        getContextManager().addExpressionValue(numberToken);
        getContextManager().incrementTokenPosition(1);
    }

    private NumberToken createNumberToken(Token token) {
        NumberToken numberToken = new NumberToken();
        numberToken.setToken(token);
        numberToken.setNumberType(NumberType.DOUBLE);
        return numberToken;
    }
}
