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

import static com.klab.interpreter.parsing.model.tokens.operators.OperatorAssociativity.LEFT_TO_RIGHT;
import static com.klab.interpreter.parsing.model.tokens.operators.OperatorAssociativity.RIGHT_TO_LEFT;
import static java.util.Objects.requireNonNull;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OperatorParseHandler extends AbstractParseHandler {
    private OperatorFactory operatorFactory;
    private ExpressionUtils expressionUtils;

    @Override
    public void handle() {
        preProcessUnaryPlusAndMinusOperators();
        OperatorToken o1 = requireNonNull(operatorFactory.getOperator(parseContextManager.tokenAt(0)));
        while (!parseContextManager.isStackEmpty()) {
            ParseToken parseToken = parseContextManager.stackPeek();
            if (!(parseToken instanceof OperatorToken)) {
                break;
            }

            OperatorToken o2 = (OperatorToken) parseToken;
            int compereResult = o1.getPriority().compareTo(o2.getPriority());
            boolean operate = o1.getAssociativity() == LEFT_TO_RIGHT && compereResult <= 0 || o1.getAssociativity() == RIGHT_TO_LEFT && compereResult <= -1;

            if (!operate) {
                break;
            }

            if (":".equals(o1.getToken().getLexeme()) && ":".equals(o2.getToken().getLexeme())) {
                parseContextManager.stackPop();
                o1 = operatorFactory.getOperator("$::", o2.getToken());
                break;
            }

            stackToExpression();
        }
        parseContextManager.stackPush(o1);
        parseContextManager.incrementTokenPosition(1);
    }

    private void preProcessUnaryPlusAndMinusOperators() {
        String token = parseContextManager.tokenAt(0).getLexeme();
        if (expressionUtils.isUnaryOperator(parseContextManager, 0)) {
            parseContextManager.tokenAt(0).setLexeme("$" + token);
        }
    }

    @Override
    public void handleStackFinish() {
        stackToExpression();
    }

    private void stackToExpression() {
        OperatorToken operatorToken = (OperatorToken) parseContextManager.stackPop();
        ExpressionNode<ParseToken> expressionNode = new ExpressionNode<>(operatorToken);
        expressionNode.addChildren(parseContextManager.expressionPopArguments(operatorToken.getArgumentsNumber()));
        parseContextManager.addExpression(expressionNode);
    }

    @Override
    public TokenClass supportedTokenClass() {
        return TokenClass.OPERATOR;
    }

    @Autowired
    void setOperatorFactory(OperatorFactory operatorFactory) {
        this.operatorFactory = operatorFactory;
    }

    @Autowired
    void setExpressionUtils(ExpressionUtils expressionUtils) {
        this.expressionUtils = expressionUtils;
    }
}
