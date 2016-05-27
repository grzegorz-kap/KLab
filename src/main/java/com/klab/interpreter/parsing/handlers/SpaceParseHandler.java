package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.matrix.MatrixNewColumnParseHandler;
import com.klab.interpreter.parsing.model.BalanceType;
import com.klab.interpreter.parsing.service.ParseContextManager;
import com.klab.interpreter.parsing.utils.ExpressionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SpaceParseHandler extends AbstractParseHandler {
    private MatrixNewColumnParseHandler matrixNewColumnParseHandler;
    private ExpressionUtils expressionUtils;

    @Override
    public void handle() {
        if (pCtxMgr.getBalanceContext().isBalanceType(BalanceType.INSIDE_MATRIX)) {
            if (tokenClassAt(-1) != TokenClass.OPERATOR || expressionUtils.isUnaryOperator(pCtxMgr, 1)) {
                matrixNewColumnParseHandler.handle();
                return;
            }
        }
        pCtxMgr.incrementTokenPosition(1);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.SPACE;
    }

    @Override
    public void setContextManager(ParseContextManager parseContextManager) {
        super.setContextManager(parseContextManager);
        matrixNewColumnParseHandler.setContextManager(parseContextManager);
    }

    @Autowired
    public void setMatrixNewColumnParseHandler(MatrixNewColumnParseHandler matrixNewColumnParseHandler) {
        this.matrixNewColumnParseHandler = matrixNewColumnParseHandler;
    }

    @Autowired
    public void setExpressionUtils(ExpressionUtils expressionUtils) {
        this.expressionUtils = expressionUtils;
    }
}
