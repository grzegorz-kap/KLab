package com.klab.interpreter.parsing.handlers.instruction;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.exception.UnexpectedKeywordException;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.model.BalanceContext;
import com.klab.interpreter.parsing.model.KeywordBalance;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BreakKeywordParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        BalanceContext balanceContext = pCtxMgr.getBalanceContext();
        check(balanceContext);
        pCtxMgr.addExpressionValue(new ParseToken(pCtxMgr.tokenAt(0), ParseClass.BREAK_FOR));
        pCtxMgr.incrementTokenPosition(1);
        pCtxMgr.setInstructionStop(true);
    }

    public void check(BalanceContext balanceContext) {
        if (!balanceContext.isKeywordBalance(KeywordBalance.FOR_INSTRUCTION)) {
            throw new UnexpectedKeywordException("break", pCtxMgr.getParseContext());
        }
        if (pCtxMgr.expressionSize() != 0) {
            throw new UnexpectedKeywordException("break", pCtxMgr.getParseContext());
        }
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.BREAK_KEYWORD;
    }
}
