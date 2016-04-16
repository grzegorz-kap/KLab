package com.klab.interpreter.parsing.handlers.word;

import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.model.tokens.IdentifierToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IdentifierParseHandler extends AbstractParseHandler {

    private IdentifierMapper identifierMapper;

    @Override
    public void handle() {
        IdentifierToken identifierToken = new IdentifierToken(pCtxMgr.tokenAt(0));
        identifierToken.setAddress(identifierMapper.registerMainIdentifier(identifierToken.getId()));
        pCtxMgr.addExpressionValue(identifierToken);
        pCtxMgr.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return null;
    }

    @Autowired
    private void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }

}
