package interpreter.parsing.handlers.instruction;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.exception.UnexpectedKeywordException;
import interpreter.parsing.handlers.AbstractParseHandler;
import interpreter.parsing.model.BalanceContext;
import interpreter.parsing.model.KeywordBalance;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ContinueKeywordParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        check();
        pCtxMgr.addExpressionValue(new ParseToken(pCtxMgr.tokenAt(0), ParseClass.CONTINUE_FOR));
        pCtxMgr.incrementTokenPosition(1);
        pCtxMgr.setInstructionPrint(true);
    }

    public void check() {
        BalanceContext balanceContext = pCtxMgr.getBalanceContext();
        if (!balanceContext.isKeywordBalance(KeywordBalance.FOR_INSTRUCTION)) {
            throw new UnexpectedKeywordException("continue", pCtxMgr.getParseContext());
        }
        if (pCtxMgr.expressionSize() != 0) {
            throw new UnexpectedKeywordException("continue", pCtxMgr.getParseContext());
        }
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.CONTINUE_KEYWORD;
    }
}
