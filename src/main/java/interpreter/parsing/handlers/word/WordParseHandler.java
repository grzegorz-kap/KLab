package interpreter.parsing.handlers.word;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.handlers.AbstractParseHandler;
import interpreter.parsing.service.ParseContextManager;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WordParseHandler extends AbstractParseHandler {

	private IdentifierParseHandler identifierParseHandler;
	private CallParseHandler callParseHandler;

	@Override
	public void handle() {
		if (Objects.nonNull(parseContextManager.tokenAt(1))
				&& parseContextManager.tokenAt(1).getTokenClass().equals(TokenClass.OPEN_PARENTHESIS)) {
			callParseHandler.handle();
		} else {
			identifierParseHandler.handle();
		}
	}

	@Override
	public TokenClass getSupportedTokenClass() {
		return TokenClass.WORD;
	}
	
	@Override
	public void setContextManager(ParseContextManager parseContextManager) {
		this.parseContextManager = parseContextManager;
		identifierParseHandler.setContextManager(parseContextManager);
		callParseHandler.setContextManager(parseContextManager);
	};

	@Autowired
	public void setIdentifierParseHandler(IdentifierParseHandler identifierParseHandler) {
		this.identifierParseHandler = identifierParseHandler;
	}

	@Autowired
	public void setCallParseHandler(CallParseHandler callParseHandler) {
		this.callParseHandler = callParseHandler;
	}

}
