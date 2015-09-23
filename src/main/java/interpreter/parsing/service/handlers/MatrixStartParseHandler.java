package interpreter.parsing.service.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.tokens.MatrixStartToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixStartParseHandler extends AbstractParseHandler implements ParseHandler {

    @PostConstruct
    private void init() {
        supportedTokenClass = TokenClass.OPEN_BRACKET;
    }

    @Override
    public void handle() {
        ParseToken parseToken = new MatrixStartToken(parseContextManager.tokenAt(0));
        parseContextManager.addExpressionNode(parseToken);
        parseContextManager.stackPush(parseToken);
        parseContextManager.incrementTokenPosition(1);
    }
}
