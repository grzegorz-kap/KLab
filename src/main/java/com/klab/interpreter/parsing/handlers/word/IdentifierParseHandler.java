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
    private IdentifierMapper mapper;

    @Override
    public void handle() {
        IdentifierToken idToken = new IdentifierToken(tokenAt(0));
        String variableName = idToken.getId();
        Integer address = mapper.registerMainIdentifier(variableName);
        idToken.setAddress(address);
        addExpressionValue(idToken);
        incrementTokenPosition(1);
    }

    @Override
    public TokenClass supportedTokenClass() {
        return null;
    }

    @Autowired
    private void setMapper(IdentifierMapper mapper) {
        this.mapper = mapper;
    }
}
