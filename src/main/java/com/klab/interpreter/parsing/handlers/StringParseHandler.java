package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.Token;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StringParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        Token string = parseContextManager.tokenAt(0);
        ParseToken parseToken = new ParseToken(string, ParseClass.STRING);
        parseContextManager.addExpressionValue(parseToken);
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.STRING;
    }
}
