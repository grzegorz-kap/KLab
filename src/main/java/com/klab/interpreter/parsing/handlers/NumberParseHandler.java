package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.ExpressionValue;
import com.klab.interpreter.parsing.model.tokens.NumberToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.klab.interpreter.types.NumericType.COMPLEX_DOUBLE;
import static com.klab.interpreter.types.NumericType.DOUBLE;

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