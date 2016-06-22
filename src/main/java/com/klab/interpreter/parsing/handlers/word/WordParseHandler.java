package com.klab.interpreter.parsing.handlers.word;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.handlers.CallParseHandler;
import com.klab.interpreter.parsing.service.ParseContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WordParseHandler extends AbstractParseHandler {
    private IdentifierParseHandler identifierParseHandler;
    private CallParseHandler callParseHandler;

    @Override
    public void handle() {
        if (parseContextManager.tokenAt(1) != null && parseContextManager.tokenAt(1).getTokenClass().equals(TokenClass.OPEN_PARENTHESIS)) {
            callParseHandler.handle();
        } else {
            identifierParseHandler.handle();
        }
    }

    @Override
    public TokenClass supportedTokenClass() {
        return TokenClass.WORD;
    }

    @Override
    public void setParseContextManager(ParseContextManager parseContextManager) {
        this.parseContextManager = parseContextManager;
        identifierParseHandler.setParseContextManager(parseContextManager);
        callParseHandler.setParseContextManager(parseContextManager);
    }

    @Autowired
    void setIdentifierParseHandler(IdentifierParseHandler identifierParseHandler) {
        this.identifierParseHandler = identifierParseHandler;
    }

    @Autowired
    void setCallParseHandler(CallParseHandler callParseHandler) {
        this.callParseHandler = callParseHandler;
    }
}
