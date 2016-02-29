package interpreter.parsing.handlers.instruction;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.exception.WrongIfInstructionException;
import interpreter.parsing.handlers.AbstractParseHandler;
import interpreter.parsing.model.KeywordBalance;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static interpreter.parsing.exception.WrongIfInstructionException.ELSEIF_NOT_EXPECTED_HERE;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ElseifKeywordParseHandler extends AbstractParseHandler {

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.ELSEIF_KEYWORD;
    }

    @Override
    public void handle() {
        checkCorrectExpression();
        checkKeywordBalance();
        pCtxMgr.addExpressionValue(new ParseToken(pCtxMgr.tokenAt(0), ParseClass.ELSEIF_KEYWORD));
        pCtxMgr.incrementTokenPosition(1);
    }

    private void checkCorrectExpression() {
        if (pCtxMgr.expressionSize() != 0) {
            throw new WrongIfInstructionException(ELSEIF_NOT_EXPECTED_HERE, pCtxMgr.getParseContext());
        }
    }

    private void checkKeywordBalance() {
        if (!pCtxMgr.getBalanceContext().isKeywordBalance(KeywordBalance.IF_INSTRUCTION)) {
            throw new WrongIfInstructionException(ELSEIF_NOT_EXPECTED_HERE, pCtxMgr.getParseContext());
        }
    }
}
