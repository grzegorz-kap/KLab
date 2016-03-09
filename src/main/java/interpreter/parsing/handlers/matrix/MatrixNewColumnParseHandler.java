package interpreter.parsing.handlers.matrix;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.handlers.AbstractParseHandler;
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
        if (!stackHelper.stackToExpressionUntilParseClass(pCtxMgr, ParseClass.MATRIX_START)) {
            throw new RuntimeException();
        }
        pCtxMgr.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return null;
    }

    @Autowired
    public void setStackHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }
}
