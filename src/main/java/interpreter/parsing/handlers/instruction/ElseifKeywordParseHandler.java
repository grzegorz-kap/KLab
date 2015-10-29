package interpreter.parsing.handlers.instruction;

import static interpreter.parsing.exception.WrongIfInstructionException.ELSEIF_NOT_EXPECTED_HERE;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.exception.WrongIfInstructionException;
import interpreter.parsing.handlers.AbstractParseHandler;
import interpreter.parsing.model.KeywordBalance;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;

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
		ParseToken parseToken = new ParseToken();
		parseToken.setParseClass(ParseClass.ELSEIF_KEYWORD);
		parseToken.setToken(parseContextManager.tokenAt(0));
		parseContextManager.addExpressionValue(parseToken);
		parseContextManager.incrementTokenPosition(1);
	}

	private void checkCorrectExpression() {
		if (parseContextManager.expressionSize() != 0) {
			throw new WrongIfInstructionException(ELSEIF_NOT_EXPECTED_HERE, parseContextManager.getParseContext());
		}
	}

	private void checkKeywordBalance() {
		if (!parseContextManager.getBalanceContext().isKeywordBalance(KeywordBalance.IF_INSTRUCTION)) {
			throw new WrongIfInstructionException(ELSEIF_NOT_EXPECTED_HERE, parseContextManager.getParseContext());
		}
	}

}