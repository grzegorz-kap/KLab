package interpreter.parsing.handlers.instruction;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.exception.WrongForInstructionException;
import interpreter.parsing.handlers.AbstractParseHandler;
import interpreter.parsing.model.KeywordBalance;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static interpreter.parsing.exception.WrongForInstructionException.ENDFOR_KEYWORD_NOT_EXPECTED_HERE;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EndForKeywordParseHandler extends AbstractParseHandler {
    @Override
    public void handle() {
        checkIfExpected();
        parseContextManager.addExpressionValue(new ParseToken(parseContextManager.tokenAt(0), ParseClass.ENDFOR_KEYWORD));
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.ENDFOR_KEYWORD;
    }

    private void checkIfExpected() {
        if (parseContextManager.expressionSize() != 0) {
            throw new WrongForInstructionException(ENDFOR_KEYWORD_NOT_EXPECTED_HERE, parseContextManager.getParseContext());
        }
        if (!parseContextManager.getBalanceContext().isKeywordBalance(KeywordBalance.FOR_INSTRUCTION)) {
            throw new WrongForInstructionException(ENDFOR_KEYWORD_NOT_EXPECTED_HERE, parseContextManager.getParseContext());
        }
    }
}