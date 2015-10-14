package interpreter.parsing.handlers;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.tokens.ParenthesisParseToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OpenParenthesisParseHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        Token token = parseContextManager.tokenAt(0);
        parseContextManager.stackPush(new ParenthesisParseToken(token, ParseClass.OPEN_PARENTHESIS));
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.OPEN_PARENTHESIS;
    }
}
