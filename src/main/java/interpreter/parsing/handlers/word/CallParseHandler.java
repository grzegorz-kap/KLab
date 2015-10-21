package interpreter.parsing.handlers.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.commons.IdentifierMapper;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.handlers.AbstractParseHandler;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.tokens.IdentifierToken;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallParseHandler extends AbstractParseHandler {

	private IdentifierMapper identifierMapper;

	@Override
	public void handle() {
		IdentifierToken identifierToken = new IdentifierToken(parseContextManager.tokenAt(0));
		identifierToken.setParseClass(ParseClass.CALL);
		identifierToken.setAddress(identifierMapper.registerMainIdentifier(identifierToken.getId()));
		parseContextManager.addExpressionNode(identifierToken);
		parseContextManager.stackPush(identifierToken);
		parseContextManager.incrementTokenPosition(2);
	}

	@Override
	public TokenClass getSupportedTokenClass() {
		return null;
	}

	@Autowired
	public void setIdentifierMapper(IdentifierMapper identifierMapper) {
		this.identifierMapper = identifierMapper;
	}

}
