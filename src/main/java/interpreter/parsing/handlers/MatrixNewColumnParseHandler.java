package interpreter.parsing.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.handlers.helpers.StackHelper;
import interpreter.parsing.model.ParseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixNewColumnParseHandler extends AbstractParseHandler {

    private StackHelper stackHelper;

    @Override
    public void handle() {
        if (!stackHelper.stackToExpressionUntilTokenClass(parseContextManager, ParseClass.MATRIX_START)) {
            throw new RuntimeException();
        }
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.COMMA;
    }

    @Autowired
    public void setStackHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }
}
