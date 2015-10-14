package interpreter.parsing.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.exception.WrongIfInstructionException;
import interpreter.parsing.model.KeywordBalance;
import interpreter.parsing.model.tokens.IfToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static interpreter.parsing.exception.WrongIfInstructionException.UNEXPECTED_IF_OR_ELSEIF_KEYWORD;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IfKeywordParseHandler extends AbstractParseHandler {

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.IF_KEYWORD;
    }

    @Override
    public void handle() {
        checkKeywordCorrectPlacement();
        parseContextManager.getBalanceContext().put(KeywordBalance.IF_INSTRUCTION);
        parseContextManager.addExpressionValue(new IfToken(parseContextManager.tokenAt(0)));
        parseContextManager.incrementTokenPosition(1);
    }

    private void checkKeywordCorrectPlacement() {
        if (parseContextManager.expressionSize() != 0) {
            throw new WrongIfInstructionException(UNEXPECTED_IF_OR_ELSEIF_KEYWORD, parseContextManager.getParseContext());
        }
    }
}
