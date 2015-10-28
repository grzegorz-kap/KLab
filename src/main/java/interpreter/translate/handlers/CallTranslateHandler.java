package interpreter.translate.handlers;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.model.tokens.CallToken;
import interpreter.translate.model.CallInstruction;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallTranslateHandler extends AbstractTranslateHandler {

	@Override
	public void handle(Expression<ParseToken> expression) {
		CallToken callToken = (CallToken) expression.getValue();
		CallInstruction callInstruction = new CallInstruction(callToken);
		callInstruction.setArgumentsNumber(expression.getChildrenCount());
		translateContextManager.addInstruction(callInstruction);
	}

	@Override
	public ParseClass getSupportedParseClass() {
		return ParseClass.CALL;
	}

}
