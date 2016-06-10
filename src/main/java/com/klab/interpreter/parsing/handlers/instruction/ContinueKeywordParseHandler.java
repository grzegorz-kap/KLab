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
public class ContinueKeywordParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        check();
        parseContextManager.addExpressionValue(new ParseToken(parseContextManager.tokenAt(0), ParseClass.CONTINUE_FOR));
        parseContextManager.incrementTokenPosition(1);
        parseContextManager.setInstructionPrint(true);
    }

    public void check() {
        BalanceContext balanceContext = parseContextManager.getBalanceContext();
        if (!balanceContext.isKeywordBalance(KeywordBalance.FOR_INSTRUCTION)) {
            throw new UnexpectedKeywordException("continue", parseContextManager.getParseContext());
        }
        if (parseContextManager.expressionSize() != 0) {
            throw new UnexpectedKeywordException("continue", parseContextManager.getParseContext());
        }
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.CONTINUE_KEYWORD;
    }
}
