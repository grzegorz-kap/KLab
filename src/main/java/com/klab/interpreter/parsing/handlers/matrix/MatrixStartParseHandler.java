package com.klab.interpreter.parsing.handlers.matrix;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.model.BalanceType;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.tokens.MatrixStartToken;
import com.klab.interpreter.parsing.service.BalanceContextService;
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
