package com.klab.interpreter.parsing.handlers.matrix;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.handlers.AbstractParseHandler;
import com.klab.interpreter.parsing.handlers.helpers.ExpressionHelper;
import com.klab.interpreter.parsing.handlers.helpers.StackHelper;
import com.klab.interpreter.parsing.handlers.helpers.TypeResolver;
import com.klab.interpreter.parsing.model.BalanceType;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.model.expression.ExpressionNode;
import com.klab.interpreter.parsing.service.BalanceContextService;
import com.klab.interpreter.parsing.service.ParseContextManager;
import com.klab.interpreter.types.NumericType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.klab.interpreter.parsing.model.ParseClass.MATRIX_START;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MatrixEndParseHandler extends AbstractParseHandler {

    private StackHelper stackHelper;
    private ExpressionHelper expressionHelper;
    private BalanceContextService balanceContextService;
    private MatrixNewRowHandler matrixNewRowHandler;
    private TypeResolver typeResolver;

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.CLOSE_BRACKET;
    }

    @Override
    public void handle() {
        handleAction();
        pCtxMgr.incrementTokenPosition(1);
    }

    public void handleAction() {
        balanceContextService.popOrThrow(pCtxMgr, BalanceType.INSIDE_MATRIX);
        endRow();
        moveStackToExpression();
        reduceExpression();
    }

    private void endRow() {
        if (pCtxMgr.expressionSize() > 0 && !pCtxMgr.expressionPeek().getValue().getParseClass().equals(ParseClass.MATRIX_VERSE)) {
            matrixNewRowHandler.handleAction();
        }
    }

    private void reduceExpression() {
        ExpressionNode<ParseToken> matrixNode = new ExpressionNode<>(null);
        List<Expression<ParseToken>> expressions = expressionHelper.popUntilParseClass(pCtxMgr, this::popUntilPredicate);
        NumericType numericType = typeResolver.resolveNumeric(expressions);
        matrixNode.visitEach(node -> node.setResolvedNumericType(numericType));
        matrixNode.setResolvedNumericType(numericType);
        matrixNode.addChildren(expressions);
        matrixNode.setValue(pCtxMgr.expressionPop().getValue());
        matrixNode.getValue().setParseClass(ParseClass.MATRIX);
        pCtxMgr.addExpression(matrixNode);
    }

    private boolean popUntilPredicate(ParseClass parseClass) {
        return parseClass.equals(MATRIX_START);
    }

    private void moveStackToExpression() {
        if (!stackHelper.stackToExpressionUntilParseClass(pCtxMgr, MATRIX_START)) {
            throw new RuntimeException();
        }
        pCtxMgr.stackPop();
    }

    @Override
    public void setContextManager(ParseContextManager parseContextManager) {
        super.setContextManager(parseContextManager);
        matrixNewRowHandler.setContextManager(parseContextManager);
    }

    @Autowired
    public void setStackHelper(StackHelper stackHelper) {
        this.stackHelper = stackHelper;
    }

    @Autowired
    public void setExpressionHelper(ExpressionHelper expressionHelper) {
        this.expressionHelper = expressionHelper;
    }

    @Autowired
    public void setBalanceContextService(BalanceContextService balanceContextService) {
        this.balanceContextService = balanceContextService;
    }

    @Autowired
    public void setMatrixNewRowHandler(MatrixNewRowHandler matrixNewRowHandler) {
        this.matrixNewRowHandler = matrixNewRowHandler;
    }

    @Autowired
    public void setTypeResolver(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }
}
