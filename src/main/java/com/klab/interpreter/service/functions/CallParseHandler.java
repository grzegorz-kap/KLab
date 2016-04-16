package com.klab.interpreter.service.functions;

import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.model.BalanceType;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.service.BalanceContextService;
import com.klab.interpreter.service.functions.model.CallToken;
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
        CallToken callToken = new CallToken(pCtxMgr.tokenAt(0), ParseClass.CALL_START);
        callToken.setVariableAddress(identifierMapper.getMainAddress(callToken.getCallName()));
        callToken.setExternalAddress(identifierMapper.registerExternalFunction(callToken.getCallName()));
        pCtxMgr.addExpressionNode(callToken);
        pCtxMgr.stackPush(callToken);
        pCtxMgr.incrementTokenPosition(2);
        balanceContextService.add(pCtxMgr, BalanceType.FUNCTION_ARGUMENTS);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
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
