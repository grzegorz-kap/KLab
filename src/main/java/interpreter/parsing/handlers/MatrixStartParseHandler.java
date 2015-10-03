package interpreter.parsing.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.BalanceType;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.tokens.MatrixStartToken;
import interpreter.parsing.service.BalanceContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixStartParseHandler extends AbstractParseHandler {

    private BalanceContextService balanceContextService;

    @Override
    public void handle() {
        ParseToken parseToken = new MatrixStartToken(parseContextManager.tokenAt(0));
        parseToken.setParseClass(ParseClass.MATRIX_START);
        parseContextManager.addExpressionNode(parseToken);
        parseContextManager.stackPush(parseToken);
        balanceContextService.add(parseContextManager, BalanceType.INSIDE_MATRIX);
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.OPEN_BRACKET;
    }

    @Autowired
    public void setBalanceContextService(BalanceContextService balanceContextService) {
        this.balanceContextService = balanceContextService;
    }
}
