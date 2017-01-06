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
        if (parseContextManager.expressionSize() != 0) {
            throw new WrongForInstructionException(ENDFOR_KEYWORD_NOT_EXPECTED_HERE, parseContextManager.getParseContext());
        }
        if (!parseContextManager.getBalanceContext().isKeywordBalance(KeywordBalance.FOR_INSTRUCTION)) {
            throw new WrongForInstructionException(ENDFOR_KEYWORD_NOT_EXPECTED_HERE, parseContextManager.getParseContext());
        }
        parseContextManager.addExpressionValue(new ParseToken(parseContextManager.tokenAt(0), ParseClass.ENDFOR_KEYWORD));
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass supportedTokenClass() {
        return TokenClass.END_FOR_KEYWORD;
    }
}
