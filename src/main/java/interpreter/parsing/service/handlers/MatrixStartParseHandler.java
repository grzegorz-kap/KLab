package interpreter.parsing.service.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.tokens.MatrixStartToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixStartParseHandler extends AbstractParseHandler {

    @Override
    public void handle() {
        ParseToken parseToken = new MatrixStartToken(parseContextManager.tokenAt(0));
        parseToken.setParseClass(ParseClass.MATRIX_START);
        parseContextManager.addExpressionNode(parseToken);
        parseContextManager.stackPush(parseToken);
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.OPEN_BRACKET;
    }
}
