package interpreter.parsing.service.handlers;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.NumberType;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.tokens.NumberToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NumberHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        Token token = getContextManager().tokenAt(0);
        NumberToken numberToken = createNumberToken(token);
        numberToken.setParseClass(ParseClass.NUMBER);
        getContextManager().addExpressionValue(numberToken);
        getContextManager().incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.NUMBER;
    }

    private NumberToken createNumberToken(Token token) {
        NumberToken numberToken = new NumberToken();
        numberToken.setToken(token);
        numberToken.setNumberType(NumberType.DOUBLE);
        return numberToken;
    }
}
