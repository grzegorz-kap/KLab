package interpreter.parsing.handlers;

import interpreter.commons.IdentifierMapper;
import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.tokens.IdentifierToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WordParseHandler extends AbstractParseHandler {

    private IdentifierMapper identifierMapper;

    @Override
    public void handle() {
        IdentifierToken identifierToken = new IdentifierToken(parseContextManager.tokenAt(0));
        identifierToken.setAddress(identifierMapper.registerMainIdentifier(identifierToken.getId()));
        parseContextManager.addExpressionValue(identifierToken);
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.WORD;
    }

    @Autowired
    private void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }
}
