package interpreter.service.functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.commons.IdentifierMapper;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.handlers.AbstractParseHandler;
import interpreter.parsing.model.BalanceType;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.service.BalanceContextService;
import interpreter.service.functions.model.CallToken;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallParseHandler extends AbstractParseHandler {

	private IdentifierMapper identifierMapper;
	private BalanceContextService balanceContextService;

	@Override
	public void handle() {
		CallToken callToken = new CallToken(parseContextManager.tokenAt(0));
		callToken.setParseClass(ParseClass.CALL);
		callToken.setVariableAddress(identifierMapper.getMainAddress(callToken.getCallName()));
		parseContextManager.addExpressionNode(callToken);
		parseContextManager.stackPush(callToken);
		parseContextManager.incrementTokenPosition(2);
		balanceContextService.add(parseContextManager, BalanceType.FUNCTION_ARGUMENTS);
	}

	@Override
	public TokenClass getSupportedTokenClass() {
		return null;
	}

	@Autowired
	public void setIdentifierMapper(IdentifierMapper identifierMapper) {
		this.identifierMapper = identifierMapper;
	}

	@Autowired
	public void setBalanceContextService(BalanceContextService balanceContextService) {
		this.balanceContextService = balanceContextService;
	}

	@Autowired
	public void setInternalFunctionsHolder(InternalFunctionsHolder internalFunctionsHolder) {
	}
}
