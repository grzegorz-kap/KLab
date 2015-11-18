package interpreter.parsing.handlers;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.ExpressionValue;
import interpreter.parsing.model.tokens.NumberToken;
import interpreter.types.NumericType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NumberParseHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        Token token = getContextManager().tokenAt(0);
        NumberToken numberToken = createNumberToken(token);
        ExpressionValue<ParseToken> expressionValue = getContextManager().addExpressionValue(numberToken);
        expressionValue.setResolvedNumericType(numberToken.getNumericType());
        getContextManager().incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.NUMBER;
    }

    private NumberToken createNumberToken(Token token) {
        NumberToken numberToken = new NumberToken();
        numberToken.setToken(token);
        numberToken.setNumericType(resolveType(token));
        return numberToken;
    }

    private NumericType resolveType(Token token) {
        return token.getLexeme().endsWith("i") ? NumericType.COMPLEX_DOUBLE : NumericType.DOUBLE;
    }
}
