package interpreter.parsing.service.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.service.handlers.helpers.StackHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixNewColumnParseHandler extends AbstractParseHandler implements ParseHandler {

    private StackHelper stackHelper;

    public MatrixNewColumnParseHandler() {
        supportedTokenClass = TokenClass.COMMA;
    }

    @Override
    public void handle() {
        if (!stackHelper.stackToExpressionUntilTokenClass(parseContextManager, TokenClass.OPEN_BRACKET)) {
            throw new RuntimeException();
        }
        parseContextManager.incrementTokenPosition(1);
    }

    @Autowired
    public void setStackHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }
}
