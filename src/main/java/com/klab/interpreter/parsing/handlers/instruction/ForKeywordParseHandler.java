package com.klab.interpreter.parsing.handlers.instruction;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.exception.WrongForInstructionException;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.model.KeywordBalance;
import com.klab.interpreter.parsing.model.tokens.ForToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.klab.interpreter.parsing.exception.WrongForInstructionException.FOR_KEYWORD_NOT_ALLOWED_HERE;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ForKeywordParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        checkIfExpected();
        pCtxMgr.getBalanceContext().put(KeywordBalance.FOR_INSTRUCTION);
        pCtxMgr.addExpressionValue(new ForToken(pCtxMgr.tokenAt(0)));
        pCtxMgr.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.FOR_KEYWORD;
    }

    private void checkIfExpected() {
        if (pCtxMgr.expressionSize() != 0) {
            throw new WrongForInstructionException(FOR_KEYWORD_NOT_ALLOWED_HERE, pCtxMgr.getParseContext());
        }
    }
}
