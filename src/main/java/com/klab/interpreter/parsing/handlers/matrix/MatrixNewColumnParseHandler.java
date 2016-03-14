package com.klab.interpreter.parsing.handlers.matrix;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.handlers.helpers.StackHelper;
import com.klab.interpreter.parsing.model.ParseClass;
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
