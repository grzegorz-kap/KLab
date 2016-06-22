package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.model.BalanceType;
import com.klab.interpreter.parsing.model.CallToken;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.service.BalanceContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallParseHandler extends AbstractParseHandler {
    private IdentifierMapper identifierMapper;
    private BalanceContextService balanceContextService;

    @Override
    public void handle() {
        CallToken callToken = new CallToken(parseContextManager.tokenAt(0), ParseClass.CALL_START);
        callToken.setVariableAddress(identifierMapper.getMainAddress(callToken.getCallName()));
        callToken.setExternalAddress(identifierMapper.registerExternalFunction(callToken.getCallName()));
        parseContextManager.addExpressionNode(callToken);
        parseContextManager.stackPush(callToken);
        parseContextManager.incrementTokenPosition(2);
        balanceContextService.add(parseContextManager, BalanceType.FUNCTION_ARGUMENTS);
    }

    @Override
    public TokenClass supportedTokenClass() {
        return null;
    }

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }

    @Autowired
    public void setBalanceContextService(BalanceContextService balanceContextService) {
        this.balanceContextService = balanceContextService;
    }
}
