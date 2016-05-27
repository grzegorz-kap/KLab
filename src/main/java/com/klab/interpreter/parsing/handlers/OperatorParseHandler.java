package com.klab.interpreter.parsing.handlers;

import com.klab.interpreter.lexer.model.TokenClass;
import com.klab.interpreter.parsing.factory.operator.OperatorFactory;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.ExpressionNode;
import com.klab.interpreter.parsing.model.tokens.operators.OperatorToken;
import com.klab.interpreter.parsing.utils.ExpressionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.klab.interpreter.parsing.model.tokens.operators.OperatorAssociativity.LEFT_TO_RIGHT;
import static com.klab.interpreter.parsing.model.tokens.operators.OperatorAssociativity.RIGHT_TO_LEFT;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OperatorParseHandler extends AbstractParseHandler {
    private OperatorFactory operatorFactory;

    @Autowired
    private ExpressionUtils expressionUtils;

    @Override
    public void handle() {
        preProcessUnaryPlusAndMinusOperators();
        OperatorToken o1 = operatorFactory.getOperator(getContextManager().tokenAt(0));
        if (Objects.isNull(o1)) {
            throw new RuntimeException();
        }
        while (!pCtxMgr.isStackEmpty()) {
            ParseToken parseToken = pCtxMgr.stackPeek();
            if (!(parseToken instanceof OperatorToken)) {
                break;
            }

            OperatorToken o2 = (OperatorToken) parseToken;
            int compereResult = o1.getPriority().compareTo(o2.getPriority());
            boolean operate = o1.getAssociativity() == LEFT_TO_RIGHT && compereResult <= 0 ||
                    o1.getAssociativity() == RIGHT_TO_LEFT && compereResult <= -1;

            if (!operate) {
                break;
            }

            if (":".equals(o1.getToken().getLexeme()) && ":".equals(o2.getToken().getLexeme())) {
                pCtxMgr.stackPop();
                o1 = operatorFactory.getOperator("$::", o2.getToken());
                break;
            }

            stackToExpression();
        }
        pCtxMgr.stackPush(o1);
        pCtxMgr.incrementTokenPosition(1);
    }

    private void preProcessUnaryPlusAndMinusOperators() {
        String token = pCtxMgr.tokenAt(0).getLexeme();
        if (expressionUtils.isUnaryOperator(pCtxMgr, 0)) {
            pCtxMgr.tokenAt(0).setLexeme("$" + token);
        }
    }

    @Override
    public void handleStackFinish() {
        stackToExpression();
    }

    private void stackToExpression() {
        OperatorToken operatorToken = (OperatorToken) pCtxMgr.stackPop();
        ExpressionNode<ParseToken> expressionNode = new ExpressionNode<>(operatorToken);
        expressionNode.addChildren(pCtxMgr.expressionPopArguments(operatorToken.getArgumentsNumber()));
        pCtxMgr.addExpression(expressionNode);
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.OPERATOR;
    }

    @Autowired
    public void setOperatorFactory(OperatorFactory operatorFactory) {
        this.operatorFactory = operatorFactory;
    }
}
