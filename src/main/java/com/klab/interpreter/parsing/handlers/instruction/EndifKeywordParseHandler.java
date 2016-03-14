package com.klab.interpreter.parsing.handlers.instruction;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.exception.WrongIfInstructionException;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.model.KeywordBalance;
import com.klab.interpreter.parsing.model.tokens.EndifToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.klab.interpreter.parsing.exception.WrongIfInstructionException.KEYWORD_ENDIF_CANNOT_BE_USED_WITHOUT_IF;
import static com.klab.interpreter.parsing.exception.WrongIfInstructionException.KEYWORD_ENDIF_NOT_EXPECTED_HERE;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EndifKeywordParseHandler extends AbstractParseHandler {

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.ENDIF_KEYWORD;
    }

    @Override
    public void handle() {
        checkCorrectExpressionSize();
        checkKeywordBalance();
        pCtxMgr.addExpressionValue(new EndifToken(pCtxMgr.tokenAt(0)));
        pCtxMgr.getBalanceContext().popKeyword();
        pCtxMgr.incrementTokenPosition(1);
        pCtxMgr.setInstructionStop(true);
    }

    private void checkKeywordBalance() {
        if (!pCtxMgr.getBalanceContext().isKeywordBalance(KeywordBalance.IF_INSTRUCTION)) {
            throw new WrongIfInstructionException(KEYWORD_ENDIF_CANNOT_BE_USED_WITHOUT_IF, pCtxMgr.getParseContext());
        }
    }

    private void checkCorrectExpressionSize() {
        if (pCtxMgr.expressionSize() != 0) {
            throw new WrongIfInstructionException(KEYWORD_ENDIF_NOT_EXPECTED_HERE, pCtxMgr.getParseContext());
        }
    }
}
