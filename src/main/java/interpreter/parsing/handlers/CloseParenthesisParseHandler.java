package interpreter.parsing.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.exception.UnexpectedCloseParenthesisException;
import interpreter.parsing.handlers.helpers.StackHelper;
import interpreter.parsing.model.ParseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CloseParenthesisParseHandler extends AbstractParseHandler {

    private StackHelper stackHelper;

    @Override
    public void handle() {
        if(!stackHelper.stackToExpressionUntilParseClass(parseContextManager, ParseClass.OPEN_PARENTHESIS)){
            throw new UnexpectedCloseParenthesisException("Unexpected close parenthesis", parseContextManager.getParseContext());
        }
        parseContextManager.stackPop();
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.CLOSE_PARENTHESIS;
    }

    @Autowired
    private void setStackHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }

}
