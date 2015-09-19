package interpreter.parsing.service.handlers;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.factory.OperatorFactory;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.ExpressionNode;
import interpreter.parsing.model.tokens.operators.OperatorToken;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.LEFT_TO_RIGHT;
import static interpreter.parsing.model.tokens.operators.OperatorAssociativity.RIGHT_TO_LEFT;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OperatorHandler extends AbstractParseHandler implements ParseHandler {

    @PostConstruct
    private void init() {
        supportedTokenClass = TokenClass.OPERATOR;
    }

    @Override
    public void handle() {
        OperatorToken o1 = OperatorFactory.get(getContextManager().tokenAt(0));
        while (operateOnStack(o1)) {
            stackToExpression();
        }
        parseContextManager.stackPush(o1);
        parseContextManager.incrementTokenPosition(1);
    }

    @Override
    public void handleStackFinish() {
        stackToExpression();
    }

    private void stackToExpression() {
        OperatorToken operatorToken = (OperatorToken) parseContextManager.stackPop();
        ExpressionNode<ParseToken> expressionNode = new ExpressionNode<>(operatorToken);
        expressionNode.addChildren(parseContextManager.popExpressionArguments(operatorToken.getArgumentsNumber()));
        parseContextManager.addExpression(expressionNode);
    }

    private boolean operateOnStack(final OperatorToken o1) {
        if (parseContextManager.isStackEmpty()) {
            return false;
        }

        ParseToken parseToken = parseContextManager.stackPeek();
        if (!(parseToken instanceof OperatorToken)) {
            return false;
        }

        OperatorToken o2 = (OperatorToken) parseToken;
        int compereResult = o1.getPriority().compareTo(o2.getPriority());
        return o1.getAssociativity() == LEFT_TO_RIGHT && compereResult <= 0 ||
                o1.getAssociativity() == RIGHT_TO_LEFT && compereResult == -1;
    }
}
