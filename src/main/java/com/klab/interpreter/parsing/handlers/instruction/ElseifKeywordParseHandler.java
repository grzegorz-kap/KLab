package com.klab.interpreter.parsing.handlers.instruction;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.exception.WrongIfInstructionException;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.model.KeywordBalance;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.klab.interpreter.parsing.exception.WrongIfInstructionException.ELSEIF_NOT_EXPECTED_HERE;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ElseifKeywordParseHandler extends AbstractParseHandler {
    @Override
    public TokenClass supportedTokenClass() {
        return TokenClass.ELSEIF_KEYWORD;
    }

    @Override
    public void handle() {
        if (parseContextManager.expressionSize() != 0) {
            throw new WrongIfInstructionException(ELSEIF_NOT_EXPECTED_HERE, parseContextManager.getParseContext());
        }
        if (!parseContextManager.getBalanceContext().isKeywordBalance(KeywordBalance.IF_INSTRUCTION)) {
            throw new WrongIfInstructionException(ELSEIF_NOT_EXPECTED_HERE, parseContextManager.getParseContext());
        }
        parseContextManager.addExpressionValue(new ParseToken(parseContextManager.tokenAt(0), ParseClass.ELSEIF_KEYWORD));
        parseContextManager.incrementTokenPosition(1);
    }
}
