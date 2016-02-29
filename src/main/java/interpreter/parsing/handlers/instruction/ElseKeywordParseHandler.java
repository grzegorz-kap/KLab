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

import static interpreter.parsing.exception.WrongIfInstructionException.ELSE_KEYWORD_CAN_T_BE_USED_WITHOUT_IF;
import static interpreter.parsing.exception.WrongIfInstructionException.KEYWORD_ELSE_NOT_EXPECTED_HERE;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ElseKeywordParseHandler extends AbstractParseHandler {

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.ELSE_KEYWORD;
    }

    @Override
    public void handle() {
        checkKeywordBalance();
        checkCorrectExpression();
        pCtxMgr.addExpressionValue(new ParseToken(pCtxMgr.tokenAt(0), ParseClass.ELSE_KEYWORD));
        pCtxMgr.incrementTokenPosition(1);
        pCtxMgr.setInstructionStop(true);
    }

    private void checkCorrectExpression() {
        if (pCtxMgr.expressionSize() != 0) {
            throw new WrongIfInstructionException(KEYWORD_ELSE_NOT_EXPECTED_HERE, pCtxMgr.getParseContext());
        }
    }

    private void checkKeywordBalance() {
        if (!pCtxMgr.getBalanceContext().isKeywordBalance(KeywordBalance.IF_INSTRUCTION)) {
            throw new WrongIfInstructionException(ELSE_KEYWORD_CAN_T_BE_USED_WITHOUT_IF, pCtxMgr.getParseContext());
        }
    }
}
