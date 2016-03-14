package com.klab.interpreter.parsing.handlers.instruction;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.exception.WrongForInstructionException;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.model.KeywordBalance;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.klab.interpreter.parsing.exception.WrongForInstructionException.ENDFOR_KEYWORD_NOT_EXPECTED_HERE;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EndForKeywordParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        checkIfExpected();
        pCtxMgr.addExpressionValue(new ParseToken(pCtxMgr.tokenAt(0), ParseClass.ENDFOR_KEYWORD));
        pCtxMgr.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.ENDFOR_KEYWORD;
    }

    private void checkIfExpected() {
        if (pCtxMgr.expressionSize() != 0) {
            throw new WrongForInstructionException(ENDFOR_KEYWORD_NOT_EXPECTED_HERE, pCtxMgr.getParseContext());
        }
        if (!pCtxMgr.getBalanceContext().isKeywordBalance(KeywordBalance.FOR_INSTRUCTION)) {
            throw new WrongForInstructionException(ENDFOR_KEYWORD_NOT_EXPECTED_HERE, pCtxMgr.getParseContext());
        }
    }
}
