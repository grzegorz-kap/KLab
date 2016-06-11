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
        if (!stackHelper.stackToExpressionUntilParseClass(parseContextManager, STOP_CLASSES)) {
            throw new UnexpectedCloseParenthesisException("Unexpected close parenthesis", parseContextManager.getParseContext());
        }
        ParseToken stackPeek = parseContextManager.stackPop();
        if (stackPeek.getParseClass().equals(ParseClass.CALL_START)) {
            List<Expression<ParseToken>> expressions = expressionHelper.popUntilParseClass(parseContextManager, ParseClass.CALL_START);
            Expression<ParseToken> callNode = parseContextManager.expressionPeek();
            callNode.getValue().setParseClass(ParseClass.CALL);
            callNode.addChildren(expressions);
            CallToken callToken = (CallToken) callNode.getValue();
            callToken.setInternalFunctionAddress(internalFunctionHolder.getAddress(callToken.getCallName(), expressions.size()));
            balanceContextService.popOrThrow(parseContextManager, BalanceType.FUNCTION_ARGUMENTS);
        }
        parseContextManager.incrementTokenPosition(1);
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
