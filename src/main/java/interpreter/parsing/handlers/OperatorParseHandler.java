package interpreter.parsing.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.factory.operator.OperatorFactory;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.ExpressionNode;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.LEFT_TO_RIGHT;
import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.RIGHT_TO_LEFT;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OperatorParseHandler extends AbstractParseHandler {
    private OperatorFactory operatorFactory;

    @Override
    public void handle() {
        OperatorToken o1 = operatorFactory.getOperator(getContextManager().tokenAt(0));
        if (Objects.isNull(o1)) {
            throw new RuntimeException();
        }
        while (!parseContextManager.isStackEmpty()) {
            ParseToken parseToken = parseContextManager.stackPeek();
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
                parseContextManager.stackPop();
                o1 = operatorFactory.getOperator("$::");
                o1.setToken(o2.getToken());
                break;
            }

            stackToExpression();
        }
        parseContextManager.stackPush(o1);
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public void handleStackFinish() {
        stackToExpression();
    }

    @Override
    public TokenClass getSupportedTokenClass() {
        return TokenClass.OPERATOR;
    }

    private void stackToExpression() {
        OperatorToken operatorToken = (OperatorToken) parseContextManager.stackPop();
        ExpressionNode<ParseToken> expressionNode = new ExpressionNode<>(operatorToken);
        expressionNode.addChildren(parseContextManager.expressionPopArguments(operatorToken.getArgumentsNumber()));
        parseContextManager.addExpression(expressionNode);
    }

    @Autowired
    public void setOperatorFactory(OperatorFactory operatorFactory) {
        this.operatorFactory = operatorFactory;
    }
}
