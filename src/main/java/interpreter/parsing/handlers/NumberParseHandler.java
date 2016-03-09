package interpreter.parsing.handlers;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.ExpressionValue;
import interpreter.parsing.model.tokens.NumberToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static interpreter.types.NumericType.COMPLEX_DOUBLE;
import static interpreter.types.NumericType.DOUBLE;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NumberParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        Token token = getContextManager().tokenAt(0);
        NumberToken numberToken = new NumberToken(token, token.getLexeme().endsWith("i") ? COMPLEX_DOUBLE : DOUBLE);
        ExpressionValue<ParseToken> expressionValue = getContextManager().addExpressionValue(numberToken);
        expressionValue.setResolvedNumericType(numberToken.getNumericType());
        getContextManager().incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.NUMBER;
    }
}