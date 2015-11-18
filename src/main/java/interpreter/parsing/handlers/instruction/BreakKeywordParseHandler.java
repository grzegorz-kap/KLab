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
public class BreakKeywordParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        BalanceContext balanceContext = parseContextManager.getBalanceContext();
        check(balanceContext);
        parseContextManager.addExpressionValue(new ParseToken(parseContextManager.tokenAt(0), ParseClass.BREAK_FOR));
        parseContextManager.incrementTokenPosition(1);
        parseContextManager.setInstructionStop(true);
    }

    public void check(BalanceContext balanceContext) {
        if (!balanceContext.isKeywordBalance(KeywordBalance.FOR_INSTRUCTION)) {
            throw new UnexpectedKeywordException("break", parseContextManager.getParseContext());
        }
        if (parseContextManager.expressionSize() != 0) {
            throw new UnexpectedKeywordException("break", parseContextManager.getParseContext());
        }
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.BREAK_KEYWORD;
    }
}
