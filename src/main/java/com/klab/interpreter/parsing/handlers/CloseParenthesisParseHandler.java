package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.exception.UnexpectedCloseParenthesisException;
import com.klab.interpreter.parsing.handlers.helpers.ExpressionHelper;
import com.klab.interpreter.parsing.handlers.helpers.StackHelper;
import com.klab.interpreter.parsing.model.BalanceType;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.parsing.service.BalanceContextService;
import com.klab.interpreter.service.functions.InternalFunctionsHolder;
import com.klab.interpreter.service.functions.model.CallToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CloseParenthesisParseHandler extends AbstractParseHandler {
    private static final ParseClass[] STOP_CLASSES = new ParseClass[]{ParseClass.OPEN_PARENTHESIS, ParseClass.CALL_START};
    private StackHelper stackHelper;
    private ExpressionHelper expressionHelper;
    private BalanceContextService balanceContextService;
    private InternalFunctionsHolder internalFunctionHolder;

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.CLOSE_PARENTHESIS;
    }

    @Override
    public void handle() {
        moveStackToExpression();
        ParseToken stackPeek = pCtxMgr.stackPop();
        if (isFunctionCallEnd(stackPeek)) {
            handleFunctionEnd();
        }
        pCtxMgr.incrementTokenPosition(1);
    }

    private void handleFunctionEnd() {
        List<Expression<ParseToken>> expressions = expressionHelper.popUntilParseClass(pCtxMgr, this::isCallToken);
        Expression<ParseToken> callNode = pCtxMgr.expressionPeek();
        callNode.getValue().setParseClass(ParseClass.CALL);
        setupInternalFunctionAddress(expressions, callNode);
        callNode.addChildren(expressions);
        balanceContextService.popOrThrow(pCtxMgr, BalanceType.FUNCTION_ARGUMENTS);
    }

    private void setupInternalFunctionAddress(List<Expression<ParseToken>> expressions, Expression<ParseToken> callNode) {
        CallToken callToken = (CallToken) callNode.getValue();
        Integer address = internalFunctionHolder.getAddress(callToken.getCallName(), expressions.size());
        callToken.setInternalFunctionAddress(address);
    }

    private boolean isFunctionCallEnd(ParseToken stackPeek) {
        return stackPeek.getParseClass().equals(ParseClass.CALL_START);
    }

    private boolean isCallToken(ParseClass parseClass) {
        return parseClass.equals(ParseClass.CALL_START);
    }

    private void moveStackToExpression() {
        if (!stackHelper.stackToExpressionUntilParseClass(pCtxMgr, STOP_CLASSES)) {
            throw new UnexpectedCloseParenthesisException("Unexpected close parenthesis", pCtxMgr.getParseContext());
        }
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
    public void setInternalFunctionHolder(InternalFunctionsHolder internalFunctionHolder) {
        this.internalFunctionHolder = internalFunctionHolder;
    }
}
