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
        parseContextManager.addExpressionValue(createElseParseToken());
        parseContextManager.incrementTokenPosition(1);
        parseContextManager.setInstructionStop(true);
    }

    private ParseToken createElseParseToken() {
        ParseToken parseToken = new ParseToken();
        parseToken.setParseClass(ParseClass.ELSE_KEYWORD);
        parseToken.setToken(parseContextManager.tokenAt(0));
        return parseToken;
    }

    private void checkCorrectExpression() {
        if (parseContextManager.expressionSize() != 0) {
            throw new WrongIfInstructionException(KEYWORD_ELSE_NOT_EXPECTED_HERE,
                    parseContextManager.getParseContext());
        }
    }

    private void checkKeywordBalance() {
        if (!parseContextManager.getBalanceContext().isKeywordBalance(KeywordBalance.IF_INSTRUCTION)) {
            throw new WrongIfInstructionException(ELSE_KEYWORD_CAN_T_BE_USED_WITHOUT_IF,
                    parseContextManager.getParseContext());
        }
    }
}
