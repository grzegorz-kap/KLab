package interpreter.parsing.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.exception.UnexpectedCloseParenthesisException;
import interpreter.parsing.handlers.helpers.ExpressionHelper;
import interpreter.parsing.handlers.helpers.StackHelper;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CloseParenthesisParseHandler extends AbstractParseHandler {

	private static final ParseClass[] STOP_CLASSES = new ParseClass[] { ParseClass.OPEN_PARENTHESIS, ParseClass.CALL };

	private StackHelper stackHelper;
	private ExpressionHelper expressionHelper;

	@Override
	public TokenClass getSupportedTokenClass() {
		return TokenClass.CLOSE_PARENTHESIS;
	}

	@Override
	public void handle() {
		moveStackToExpression();
		ParseToken stackPeek = parseContextManager.stackPop();
		if (stackPeek.getParseClass().equals(ParseClass.CALL)) {
			reduceExpression();
		}
		parseContextManager.incrementTokenPosition(1);
	}

	private void reduceExpression() {
		List<Expression<ParseToken>> expressions;
		expressions = expressionHelper.popUntilParseClass(parseContextManager, this::isCallToken);
		Expression<ParseToken> callNode = parseContextManager.expressionPeek();
		callNode.addChildren(expressions);
	}

	private boolean isCallToken(ParseClass parseClass) {
		return parseClass.equals(ParseClass.CALL);
	}

	private void moveStackToExpression() {
		if (!stackHelper.stackToExpressionUntilParseClass(parseContextManager, STOP_CLASSES)) {
			throw new UnexpectedCloseParenthesisException("Unexpected close parenthesis",
					parseContextManager.getParseContext());
		}
	}

	@Autowired
	public void setStackHelper(StackHelper stackHelper) {
		this.stackHelper = stackHelper;
	}

	@Autowired
	public void setExpressionHelper(ExpressionHelper expressionHelper) {
		this.expressionHelper = expressionHelper;
	}

}
