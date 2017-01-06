package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixAllParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        parseContextManager.addExpressionValue(new ParseToken(parseContextManager.tokenAt(0), ParseClass.MATRIX_ALL));
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass supportedTokenClass() {
        return TokenClass.MATRIX_ALL;
    }
}
