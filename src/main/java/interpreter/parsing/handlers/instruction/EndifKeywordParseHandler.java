package interpreter.parsing.handlers.instruction;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.exception.WrongIfInstructionException;
import interpreter.parsing.handlers.AbstractParseHandler;
import interpreter.parsing.model.KeywordBalance;
import interpreter.parsing.model.tokens.EndifToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static interpreter.parsing.exception.WrongIfInstructionException.KEYWORD_ENDIF_CANNOT_BE_USED_WITHOUT_IF;
import static interpreter.parsing.exception.WrongIfInstructionException.KEYWORD_ENDIF_NOT_EXPECTED_HERE;

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
        parseContextManager.addExpressionValue(new EndifToken(parseContextManager.tokenAt(1)));
        parseContextManager.getBalanceContext().popKeyword();
        parseContextManager.incrementTokenPosition(1);
        parseContextManager.setInstructionStop(true);
    }

    private void checkKeywordBalance() {
        if (!parseContextManager.getBalanceContext().isKeywordBalance(KeywordBalance.IF_INSTRUCTION)) {
            throw new WrongIfInstructionException(KEYWORD_ENDIF_CANNOT_BE_USED_WITHOUT_IF, parseContextManager.getParseContext());
        }
    }

    private void checkCorrectExpressionSize() {
        if (parseContextManager.expressionSize() != 0) {
            throw new WrongIfInstructionException(KEYWORD_ENDIF_NOT_EXPECTED_HERE, parseContextManager.getParseContext());
        }
    }
}
