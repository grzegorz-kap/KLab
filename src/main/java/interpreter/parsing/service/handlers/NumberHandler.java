package interpreter.parsing.service.handlers;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.NumberType;
import interpreter.parsing.model.tokens.NumberToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NumberHandler extends AbstractParseHandler {

    @PostConstruct
    private void init() {
        supportedTokenClass = TokenClass.NUMBER;
    }

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
